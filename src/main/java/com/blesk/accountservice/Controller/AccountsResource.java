package com.blesk.accountservice.Controller;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.AccountRoles;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Service.Emails.EmailsServiceImpl;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountsResource {

    @Value("${blesk.javamailer.url.account-activation}")
    private String activationUrl;

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private AccountsServiceImpl accountsService;

    private EmailsServiceImpl emailsService;

    @Autowired
    public AccountsResource(AccountsServiceImpl accountsService, EmailsServiceImpl emailsService) {
        this.accountsService = accountsService;
        this.emailsService = emailsService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Accounts> createAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        accounts.setActivations(new Activations(UUID.randomUUID().toString()));
        Accounts account = this.accountsService.createAccount(accounts, new String[]{"ROLE_SYSTEM", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT", "ROLE_COURIER"});
        if (account == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);
        }

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(account);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(account.getAccountId(), httpServletRequest, httpServletResponse)).withRel("account"));

        Map<String, Object> variables = new HashMap<>();
        variables.put("activationUrl", String.format(this.activationUrl, account.getAccountId(), account.getActivations().getToken()));
        this.emailsService.sendHtmlMesseage("Registrácia", "signupactivation", variables, account);

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Boolean result;
        try {
            result = this.accountsService.softDeleteAccount(accountId);
        } catch (AccountServiceException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw ex;
        }

        if (!result) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts, @PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Accounts account = this.accountsService.getAccount(accountId, false);
        if (account == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_ACCOUNT);
        }

        account.setUserName(accounts.getUserName());
        account.setEmail(accounts.getEmail());
        account.setPassword(accounts.getPassword());
        account.setConfirmPassword(accounts.getConfirmPassword());

        for (AccountRoles accountRole : account.getAccountRoles()) {
            for (AccountRoles accountRoles : accounts.getAccountRoles()) {
                if (accountRoles.getDeleted() == null) {
                    account.addRole(accountRoles);
                } else if (accountRoles.getDeleted()) {
                    account.removeRole(accountRole);
                } else {
                    accountRole.setRoles(accountRoles.getRoles());
                }
            }
        }

        if (!this.accountsService.updateAccount(account, new String[]{"ROLE_SYSTEM", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT", "ROLE_COURIER"})) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.UPDATE_ACCOUNT);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Accounts> retrieveAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        Accounts accounts = this.accountsService.getAccount(accountId, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (accounts == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_ACCOUNT);
        }

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(accounts);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(accountId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(AccountsResource.DEFAULT_NUMBER, AccountsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-accounts"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Accounts>> retrieveAllAccounts(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        List<Accounts> accounts = this.accountsService.getAllAccounts(pageNumber, pageSize, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (accounts.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS);
        }

        CollectionModel<List<Accounts>> collectionModel = new CollectionModel(accounts);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/accounts/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Accounts>> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_ACCOUNTS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new AccountServiceException(Messages.AUTH_EXCEPTION);
        }

        if (search.get(Keys.PAGINATION) == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.PAGINATION_ERROR);
        }

        Map<String, Object> accounts = this.accountsService.searchForAccount(search, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (accounts == null || accounts.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new AccountServiceException(Messages.SEARCH_ERROR);
        }

        CollectionModel<List<Accounts>> collectionModel = new CollectionModel((List<Accounts>) accounts.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) accounts.get("hasPrev")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        }
        if ((boolean) accounts.get("hasNext")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        }

        return collectionModel;
    }
}