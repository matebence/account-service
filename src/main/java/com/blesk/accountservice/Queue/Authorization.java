package com.blesk.accountservice.Queue;

import com.blesk.accountservice.AccountServiceApplication;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Activations.ActivationServiceImpl;
import com.blesk.accountservice.Service.Emails.EmailsServiceImpl;
import com.blesk.accountservice.Service.Logins.LoginsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import org.hibernate.TransientPropertyValueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class Authorization {

    @Value("${blesk.javamailer.url.forget-password}")
    private String resetPasswordUrl;

    @Value("${blesk.javamailer.url.account-activation}")
    private String activationUrl;

    private AccountsServiceImpl accountsService;

    private PasswordsServiceImpl passwordsService;

    private LoginsServiceImpl loginsService;

    private ActivationServiceImpl activationService;

    private EmailsServiceImpl emailsService;

    @Autowired
    public Authorization(AccountsServiceImpl accountsService, PasswordsServiceImpl passwordsService, LoginsServiceImpl loginsService, ActivationServiceImpl activationService, EmailsServiceImpl emailsService) {
        this.accountsService = accountsService;
        this.passwordsService = passwordsService;
        this.loginsService = loginsService;
        this.activationService = activationService;
        this.emailsService = emailsService;
    }

    @RabbitListener(queues = "blesk.verifyAccountQueue")
    public Accounts verifyAccountForSigningIn(String userName) throws ListenerExecutionFailedException {
        try {
            return this.accountsService.getAccountInformations(userName);
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.lastLoginQueue")
    public Boolean recordLastSuccessfullLogin(Logins logins) throws ListenerExecutionFailedException {
        try {
            Accounts accounts = this.accountsService.getAccount(logins.getAccounts().getAccountId());
            if (accounts.getPasswords() != null)
                this.passwordsService.deletePasswordToken(accounts.getPasswords().getPasswordResetTokenId());

            return this.loginsService.updateLogin(logins);
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.createAccountQueue")
    public Accounts createNewPublicAccount(Accounts accounts) throws ListenerExecutionFailedException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Accounts>> violations = validator.validate(accounts, Accounts.validationWithEncryption.class);

        try {
            HashMap<String, String> validation = new HashMap<>();
            if (!violations.isEmpty()) {
                for (ConstraintViolation<Accounts> violation : violations) {
                    validation.put(violation.getPropertyPath().toString(), violation.getMessage());
                }
                accounts.setValidations(validation);
                return accounts;
            }

            accounts.setCreatedBy(AccountServiceApplication.SYSTEM);
            Accounts account = this.accountsService.createAccount(accounts, new String[]{"CLIENT_ROLE", "COURIER_ROLE"}).getAccounts();
            Map<String, Object> variables = new HashMap<>();
            variables.put("activationUrl", String.format(this.activationUrl, account.getAccountId(), account.getActivations().getToken()));
            this.emailsService.sendHtmlMesseage("Registrácia", "signupactivation", variables, account);

            return account;
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Accounts();
        }
    }

    @RabbitListener(queues = "blesk.verifyActivationTokenQueue")
    public Boolean verifyActivationTokenForNewAccount(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            Boolean result = this.activationService.validateActivationToken(accounts.getAccountId(), accounts.getActivations().getToken());
            if (result) {
                Accounts account = this.accountsService.getAccount(accounts.getAccountId());
                account.setActivated(result);

                if (this.accountsService.updateAccount(account))
                    return result;
            }
            return Boolean.FALSE;
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
    }

    @RabbitListener(queues = "blesk.forgetPasswordQueue")
    public Passwords recoverAccountWithForgetPassword(String email) throws ListenerExecutionFailedException {
        try {
            Passwords passwords = this.passwordsService.createPasswordToken(new Passwords(this.accountsService.findAccountByEmail(email), UUID.randomUUID().toString()));
            Map<String, Object> variables = new HashMap<>();
            variables.put("resetPasswordUrl", String.format(this.resetPasswordUrl, passwords.getAccounts().getAccountId(), passwords.getToken()));
            this.emailsService.sendHtmlMesseage("Zabudnuté heslo", "forgetpassword", variables, passwords.getAccounts());

            return passwords;
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return new Passwords();
        }
    }

    @RabbitListener(queues = "blesk.verifyPasswordTokenQueue")
    public Boolean verifyPasswordTokenForForgetPassword(Accounts accounts) throws ListenerExecutionFailedException {
        try {
            return this.passwordsService.validatePasswordToken(accounts.getAccountId(), accounts.getPasswords().getToken());
        } catch (AccountServiceException | TransientPropertyValueException | InvalidDataAccessApiUsageException ex) {
            return Boolean.FALSE;
        }
    }
}