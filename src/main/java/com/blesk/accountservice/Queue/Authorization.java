package com.blesk.accountservice.Queue;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Activations.ActivationServiceImpl;
import com.blesk.accountservice.Service.Logins.LoginsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import com.blesk.accountservice.Value.Messages;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
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
            Accounts accounts = this.accountsService.findAccountByUsername(userName);
            return accounts != null ? accounts : new Accounts();
        } catch (Exception ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.lastLoginQueue")
    public Boolean recordLastSuccessfullLogin(Logins logins) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.getAccount(logins.getAccounts().getAccountId());
            if (accounts != null && accounts.getPasswords() != null) this.passwordsService.deletePasswordToken(accounts.getPasswords());
            return this.loginsService.updateLogin(logins);
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.createAccountQueue")
    public Accounts createNewPublicAccount(Accounts accounts) throws ListenerExecutionFailedException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Accounts>> violations = validator.validate(accounts, Accounts.advancedValidation.class);
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
                    break;
                case "account_username":
                    unique.put("userName", Messages.ACCOUNTS_USER_NAME_UNIQUE);
                    break;
                case "account_email":
                    unique.put("email", Messages.ACCOUNTS_EMAIL_UNIQUE);
                    break;
            }
            accounts.setValidations(unique);
            return accounts;

        } catch (Exception ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.verifyActivationTokenQueue")
    public Boolean verifyActivationTokenForNewAccount(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            Boolean result = this.activationService.validateActivationToken(accounts.getAccountId(), accounts.getActivations().getToken());
            if (result) {
                Accounts activatedAccount = this.accountsService.getAccount(accounts.getAccountId());
                activatedAccount.setActivated(result);

                Accounts account = this.accountsService.getAccount(accounts.getAccountId());
                if (this.accountsService.updateAccount(activatedAccount, account, new String[]{})) return result;
            }
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    @RabbitListener(queues = "blesk.forgetPasswordQueue")
    public Passwords recoverAccountWithForgetPassword(String email) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.findAccountByEmail(email);
            if (accounts == null) return new Passwords();

            Passwords passwords = this.passwordsService.createPasswordToken(new Passwords(accounts, UUID.randomUUID().toString()));
            if (passwords == null) return new Passwords();

            return passwords;
        } catch (Exception ex) {
            return new Passwords();
        }
    }

    @RabbitListener(queues = "blesk.verifyPasswordTokenQueue")
    public Boolean verifyPasswordTokenForForgetPassword(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            if (this.passwordsService.validatePasswordToken(accounts.getAccountId(), accounts.getPasswords().getToken())) return this.passwordsService.generateNewPassword(accounts.getAccountId());
            return Boolean.FALSE;
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
    }
}