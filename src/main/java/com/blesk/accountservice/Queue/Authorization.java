package com.blesk.accountservice.Queue;

import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Authorization {

    private AccountsServiceImpl accountsService;

    private PasswordsServiceImpl passwordsService;

    @Autowired
    public Authorization(AccountsServiceImpl accountsService, PasswordsServiceImpl passwordsService) {
        this.accountsService = accountsService;
        this.passwordsService = passwordsService;
    }

    @RabbitListener(queues = "blesk.verifyAccountQueue")
    public Accounts verifyAccountForSigningIn(String userName) {
        try {
            return this.accountsService.getAccountInformations(userName);
        }catch (AccountServiceException ex){
            return null;
        }
    }

    @RabbitListener(queues = "blesk.createAccountQueue")
    public Accounts createNewPublicAccount(Accounts accounts) {
        try {
            return this.accountsService.createAccount(accounts, new String[]{"CLIENT_ROLE", "COURIER_ROLE"});
        }catch (AccountServiceException ex){
            return null;
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
        }catch (AccountServiceException ex){
            return null;
        }
    }

    @RabbitListener(queues = "blesk.changePasswordQueue")
    public boolean assignNewPasswordToForgetAccount(Accounts accounts) {
        return this.passwordsService.validatePasswordResetToken(accounts.getAccountId(), accounts.getPasswords().getToken());
    }
}