package com.blesk.accountservice.Queue;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Activations.ActivationServiceImpl;
import com.blesk.accountservice.Service.Logins.LoginsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import com.blesk.accountservice.Value.Messages;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Component
public class Authorization {

    private AccountsServiceImpl accountsService;

    private LoginsServiceImpl loginsService;

    private ActivationServiceImpl activationService;

    private PasswordsServiceImpl passwordsService;

    @Autowired
    public Authorization(AccountsServiceImpl accountsService, LoginsServiceImpl loginsService, ActivationServiceImpl activationService, PasswordsServiceImpl passwordsService) {
        this.accountsService = accountsService;
        this.loginsService = loginsService;
        this.activationService = activationService;
        this.passwordsService = passwordsService;
    }

    @RabbitListener(queues = "blesk.verifyAccountQueue")
    public Accounts verifyAccountForSigningIn(String userName) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.findAccountByUsername(userName, false);
            return accounts != null ? accounts : new Accounts();
        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.lastLoginQueue")
    public Boolean recordLastSuccessfullLogin(Logins logins) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.getAccount(logins.getAccounts().getAccountId(), false);
            if (accounts != null && accounts.getPasswords() != null) return this.passwordsService.deletePasswordToken(accounts.getPasswords().getPasswordTokenId());
            return this.loginsService.updateLogin(logins);
        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.createAccountQueue")
    public Accounts createNewPublicAccount(Accounts accounts) throws ListenerExecutionFailedException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Accounts>> violations = validator.validate(accounts, Accounts.class);
        HashMap<String, String> validation = new HashMap<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Accounts> violation : violations) {
                validation.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            accounts.setValidations(validation);
            return accounts;
        }

        try {
            final String[] allowedRoles = {"ROLE_COURIER", "ROLE_CLIENT"};
            return this.accountsService.createAccount(accounts, allowedRoles);

        } catch (DataIntegrityViolationException e) {
            ConstraintViolationException exDetail = (ConstraintViolationException) e.getCause();
            HashMap<String, String> unique = new HashMap<>();

            switch (exDetail.getConstraintName()) {
                case "account_id":
                    unique.put("accountId", Messages.UNIQUE_FIELD_DEFAULT);
                case "account_username":
                    unique.put("userName", Messages.ACCOUNTS_USER_NAME_UNIQUE);
                case "account_email":
                    unique.put("email", Messages.ACCOUNTS_EMAIL_UNIQUE);
                    break;
            }
            accounts.setValidations(unique);
            return accounts;

        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.verifyActivationTokenQueue")
    public Boolean verifyActivationTokenForNewAccount(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            Boolean result = this.activationService.validateActivationToken(accounts.getAccountId(), accounts.getActivations().getToken());
            if (result) {
                Accounts account = this.accountsService.getAccount(accounts.getAccountId(), false);
                account.setActivated(result);
                if (this.accountsService.updateAccount(account, new Accounts(), new String[]{})) return result;
            }
        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    @RabbitListener(queues = "blesk.forgetPasswordQueue")
    public Passwords recoverAccountWithForgetPassword(String email) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.findAccountByEmail(email, false);
            if (accounts == null) return new Passwords();

            Passwords passwords = this.passwordsService.createPasswordToken(new Passwords(accounts, UUID.randomUUID().toString()));
            if (passwords == null) return new Passwords();

            return passwords;
        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Passwords();
        }
    }

    @RabbitListener(queues = "blesk.verifyPasswordTokenQueue")
    public Boolean verifyPasswordTokenForForgetPassword(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            return this.passwordsService.validatePasswordToken(accounts.getAccountId(), accounts.getPasswords().getToken());
        } catch (NullPointerException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
    }
}