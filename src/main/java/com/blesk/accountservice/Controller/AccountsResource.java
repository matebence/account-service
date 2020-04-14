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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AccountsResource {

    private AccountsServiceImpl accountsService;

    @Autowired
    public AccountsResource(AccountsServiceImpl accountsService) {
        this.accountsService = accountsService;
    }

    @PreAuthorize("hasRole('VIEW_ALL_ACCOUNTS')")
    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Accounts>> retrieveAllAccounts(@PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Accounts> accounts = this.accountsService.getAllAccounts(pageNumber, pageSize);
        CollectionModel<List<Accounts>> collectionModel = new CollectionModel(accounts);

        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(pageNumber, pageSize)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(++pageNumber, pageSize)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('VIEW_ACCOUNTS')")
    @GetMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Accounts> retrieveAccounts(@PathVariable long accountId) {
        Accounts accounts = this.accountsService.getAccount(accountId);

        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(accounts);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(accountId)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllAccounts(0, 10)).withRel("all-accounts"));

        return entityModel;
    }

    @PreAuthorize("hasRole('DELETE_ACCOUNTS')")
    @DeleteMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccounts(@PathVariable long accountId) {
        this.accountsService.deleteAccount(accountId);
    }

    @PreAuthorize("hasRole('CREATE_ACCOUNTS')")
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Accounts> createAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts) {
        Accounts account = this.accountsService.createAccount(accounts, new String[]{"SYSTEM_ROLE", "ADMIN_ROLE", "MANAGER_ROLE", "CLIENT_ROLE", "COURIER_ROLE"}).getAccounts();
        EntityModel<Accounts> entityModel = new EntityModel<Accounts>(account);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAccounts(account.getAccountId())).withRel("account"));

        return entityModel;
    }

    @PreAuthorize("hasRole('UPDATE_ACCOUNTS')")
    @PutMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateAccounts(@Validated(Accounts.validationWithEncryption.class) @RequestBody Accounts accounts, @PathVariable long accountId) {
        if (this.accountsService.getAccount(accountId) != null){
            accounts.setAccountId(accountId);
            this.accountsService.updateAccount(accounts);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('VIEW_ALL_ACCOUNTS')")
    @PostMapping("/accounts/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Accounts>> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search) {
        Map<String, Object> accounts = this.accountsService.searchForAccount(search);

        CollectionModel<List<Accounts>> collectionModel = new CollectionModel((List<Accounts>) accounts.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search)).withSelfRel());

        if ((boolean) accounts.get("hasPrev")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search)).withRel("hasPrev"));
        }
        if ((boolean) accounts.get("hasNext")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForAccounts(search)).withRel("hasNext"));
        }

        return collectionModel;
    }
}