package com.blesk.accountservice.Controller;

import com.blesk.accountservice.DTO.ResponseMessage;
import com.blesk.accountservice.Exceptions.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Authorizations.AuthorizationsServiceImpl;
import com.blesk.accountservice.Service.Passwords.PasswordsServiceImpl;
import com.blesk.accountservice.Values.Keys;
import com.blesk.accountservice.Values.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/public", produces = "application/json")
public class PublicController {

    private AccountsServiceImpl accountsService;

    private AuthorizationsServiceImpl authorizationsService;

    private PasswordsServiceImpl passwordsService;

    @Autowired
    public PublicController(AccountsServiceImpl accountsService, AuthorizationsServiceImpl authorizationsService, PasswordsServiceImpl passwordsService) {
        this.accountsService = accountsService;
        this.authorizationsService = authorizationsService;
        this.passwordsService = passwordsService;
    }

    @PostMapping(value = "accounts/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage verifyAccountForSigningIn(@RequestBody HashMap<String, String> account) {
        if (account.get(Keys.USER_NAME) == null)
            throw new AccountServiceException(Messages.INDEX_NOT_FOUND_EXCEPTION);

        Accounts accounts = accountsService.getAccountInformations(account.get(Keys.USER_NAME));
        boolean status = authorizationsService.send(accounts);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        if (status) {
            responseMessage.setMessage(Messages.ACCOUNT_SIGN_IN_VERIFY);
            responseMessage.setError(true);
            return responseMessage;
        } else {
            responseMessage.setMessage(Messages.ACCOUNT_SIGN_IN_ERROR);
            responseMessage.setError(false);
            return responseMessage;
        }
    }

    @PostMapping(value = "accounts/recover")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage recoverAccountWithForgetPassword(@RequestBody HashMap<String, String> account) {
        if (account.get(Keys.USER_EMAIL) == null)
            throw new AccountServiceException(Messages.INDEX_NOT_FOUND_EXCEPTION);

        Accounts accounts = accountsService.findAccountByEmail(account.get(Keys.USER_EMAIL));
        String token = UUID.randomUUID().toString();
        Passwords passwords = new Passwords();
        passwords.setAccount(accounts);
        passwords.setToken(token);
        passwords.setExpiryDate();
        passwordsService.createPasswordToken(passwords);

        return new ResponseMessage(new Timestamp(System.currentTimeMillis()).toString(), Messages.ACCOUNT_FORGET_PASSWORD, false);
    }

    @PostMapping(value = "accounts/new")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage createNewPublicAccount(@Valid @RequestBody Accounts accounts) {
        Accounts account = this.accountsService.createAccount(accounts, new String[]{"CLIENT_ROLE", "COURIER_ROLE"});
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());

        if (account != null) {
            responseMessage.setMessage(Messages.ACCOUNT_NEW_PUBLIC);
            responseMessage.setError(false);
            return responseMessage;
        } else {
            responseMessage.setMessage(Messages.ACCOUNT_NEW_ERROR);
            responseMessage.setError(true);
            return responseMessage;
        }
    }
}