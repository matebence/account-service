package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountsResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private AccountsServiceImpl accountsService;

    @Autowired
    public AccountsResource(AccountsServiceImpl accountsService) {
        this.accountsService = accountsService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Accounts> createAccounts(@Validated(Accounts.advancedValidation.class) @RequestBody Accounts accounts, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts account = this.accountsService.createAccount(accounts, new String[]{"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT", "ROLE_COURIER"});
        if (account == null) throw new AccountServiceException(Messages.CREATE_ACCOUNT, HttpStatus.BAD_REQUEST);

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(account);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(account.getAccountId(), httpServletRequest, httpServletResponse)).withRel("account"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts account = this.accountsService.getAccount(accountId);
        if (account == null) throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.NOT_FOUND);
        if(!this.accountsService.deleteAccount(account)) throw new AccountServiceException(Messages.DELETE_ACCOUNT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateAccounts(@Validated(Accounts.advancedValidation.class) @RequestBody Accounts accounts, @PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts account = this.accountsService.getAccount(accountId);
        if (account == null) throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.BAD_REQUEST);

        if (!this.accountsService.updateAccount(account, accounts, new String[]{"ROLE_SYSTEM", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT", "ROLE_COURIER"})) throw new AccountServiceException(Messages.UPDATE_ACCOUNT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Accounts> retrieveAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts accounts = this.accountsService.getAccount(accountId);
        if (accounts == null) throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.BAD_REQUEST);

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(accounts);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(accountId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(AccountsResource.DEFAULT_NUMBER, AccountsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-accounts"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Accounts>> retrieveAllAccounts(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Accounts> accounts = this.accountsService.getAllAccounts(pageNumber, pageSize);
        if (accounts == null || accounts.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Accounts>> collectionModel = new CollectionModel(accounts);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/accounts/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Accounts>> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new AccountServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> accounts = this.accountsService.searchForAccount(search);
        if (accounts == null || accounts.isEmpty()) throw new AccountServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Accounts>> collectionModel = new CollectionModel((List<Accounts>) accounts.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) accounts.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) accounts.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/accounts/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Accounts>> joinAccounts(@PathVariable String columName, @RequestBody List<Long> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Accounts> accounts = this.accountsService.getAccountsForJoin(ids, columName);
        if (accounts == null || accounts.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS, HttpStatus.BAD_REQUEST);
        return new CollectionModel(accounts);
    }
}