package com.blesk.accountservice.Controller;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Service.Accounts.AccountsServiceImpl;
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

    @PreAuthorize("hasRole('VIEW_ALL_ACCOUNTS')")
    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Accounts>> retrieveAllAccounts(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Accounts> accounts = this.accountsService.getAllAccounts(pageNumber, pageSize);
        CollectionModel<List<Accounts>> collectionModel = new CollectionModel(accounts);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('VIEW_ACCOUNTS')")
    @GetMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Accounts> retrieveAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts accounts = this.accountsService.getAccount(accountId);

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(accounts);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(accountId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(AccountsResource.DEFAULT_NUMBER, AccountsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-accounts"));

        return entityModel;
    }

    @PreAuthorize("hasRole('DELETE_ACCOUNTS')")
    @DeleteMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteAccounts(@PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.accountsService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CREATE_ACCOUNTS')")
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Accounts> createAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts account = this.accountsService.createAccount(accounts, new String[]{"ROLE_SYSTEM", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT", "ROLE_COURIER"}).getAccounts();
        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(account);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(account.getAccountId(), httpServletRequest, httpServletResponse)).withRel("account"));

        return entityModel;
    }

    @PreAuthorize("hasRole('UPDATE_ACCOUNTS')")
    @PutMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts, @PathVariable long accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Accounts account = this.accountsService.getAccount(accountId);
        if (account != null) {
            account.setUserName(accounts.getUserName());
            account.setEmail(accounts.getEmail());
            account.setPassword(accounts.getPassword());
            account.setUpdatedBy(accounts.getUpdatedBy());
            account.setDeletedBy(accounts.getDeletedBy());
            this.accountsService.updateAccount(account);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('VIEW_ALL_ACCOUNTS')")
    @PostMapping("/accounts/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Accounts>> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String, Object> accounts = this.accountsService.searchForAccount(search);

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