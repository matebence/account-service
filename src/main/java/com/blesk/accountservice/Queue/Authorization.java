package com.blesk.accountservice.Queue;

import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Activations.ActivationServiceImpl;
import com.blesk.accountservice.Service.Logins.LoginsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Component
public class Authorization {

    private AccountsServiceImpl accountsService;

    private PasswordsServiceImpl passwordsService;

    private ActivationServiceImpl activationService;

    private LoginsServiceImpl loginsService;

    @Autowired
    public Authorization(AccountsServiceImpl accountsService, PasswordsServiceImpl passwordsService, ActivationServiceImpl activationService, LoginsServiceImpl loginsService) {
        this.accountsService = accountsService;
        this.passwordsService = passwordsService;
        this.activationService = activationService;
        this.loginsService = loginsService;
    }

    @RabbitListener(queues = "blesk.verifyAccountQueue")
    public Accounts verifyAccountForSigningIn(String userName) {
        try {
            return this.accountsService.getAccountInformations(userName);
        } catch (AccountServiceException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.lastLoginQueue")
    public Boolean recordLastSuccessfullLogin(Logins logins) {
        try {
            Accounts accounts = this.accountsService.getAccount(logins.getAccount().getAccountId());
            if (accounts.getPasswords() != null)
                this.passwordsService.deletePasswordToken(accounts.getPasswords().getPasswordResetTokenId());

            return this.loginsService.updateLogin(logins);
        } catch (AccountServiceException ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.createAccountQueue")
    public Accounts createNewPublicAccount(Accounts accounts) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Accounts>> violations = validator.validate(accounts);

        try {
            HashMap<String, String> validation = new HashMap<>();
            if (!violations.isEmpty()){
                for (ConstraintViolation<Accounts> violation : violations) {
                    validation.put(violation.getPropertyPath().toString(), violation.getMessage());
                    System.out.println(violation.getPropertyPath().toString() + " = " + violation.getMessage());
                }
                accounts.setValidations(validation);
                System.out.println(validation.size());
                return accounts;
            }

            Accounts account = this.accountsService.createAccount(accounts, new String[]{"CLIENT_ROLE", "COURIER_ROLE"});
            account.setValidations(validation);
            Activations activations = new Activations();
            activations.setToken(UUID.randomUUID().toString());
            activations.setAccount(account);
            account.setActivations(activations);

            this.activationService.createActivationToken(activations);
            return account;
        } catch (AccountServiceException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.verifyActivationTokenQueue")
    public Boolean verifyActivationTokenForNewAccount(Accounts accounts) {
        try {
            return this.activationService.validateActivationToken(accounts.getAccountId(), accounts.getPasswords().getToken());
        } catch (AccountServiceException ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.forgetPasswordQueue")
    public Passwords recoverAccountWithForgetPassword(String email) {
        Accounts accounts = this.accountsService.findAccountByEmail(email);
        String token = UUID.randomUUID().toString();

        Passwords passwords = new Passwords();
        passwords.setAccount(accounts);
        passwords.setToken(token);
        passwords.setExpiryDate();

        try {
            return this.passwordsService.createPasswordToken(passwords);
        } catch (AccountServiceException ex) {
            return new Passwords();
        }
    }

    @RabbitListener(queues = "blesk.verifyPasswordTokenQueue")
    public Boolean verifyPasswordTokenForForgetPassword(Accounts accounts) {
        try {
            return this.passwordsService.validatePasswordToken(accounts.getAccountId(), accounts.getPasswords().getToken());
        } catch (AccountServiceException ex) {
            return Boolean.FALSE;
        }
    }
}