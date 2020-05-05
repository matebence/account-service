package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.AccountRoles;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
public class AccountsServiceImpl implements AccountsService {

    private AccountsDAOImpl accountDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Accounts createAccount(@Validated(Accounts.validationWithoutEncryption.class) Accounts accounts, String[] allowedRoles) {
        Set<AccountRoles> assignedRoles = new HashSet<>(accounts.getAccountRoles());
        for (AccountRoles accountRoles : assignedRoles) {
            if (!Arrays.asList(allowedRoles).contains(accountRoles.getRoles().getName())) {
                accounts.getAccountRoles().remove(accountRoles);
            } else {
                accounts.getAccountRoles().remove(accountRoles);
                accounts.addRole(accountRoles);
            }
        }

        accounts.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        accounts.setActivations(new Activations(UUID.randomUUID().toString()));
        return this.accountDAO.save(accounts);
    }

    @Override
    @Transactional
    public Boolean deleteAccount(Long accountId, boolean su) {
        if (su) {
            Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
            if (accounts == null)
                throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.NOT_FOUND);
            if (!this.accountDAO.delete("accounts", "account_id", accountId))
                throw new AccountServiceException(Messages.DELETE_ACCOUNT, HttpStatus.NOT_FOUND);
            return true;
        } else {
            Accounts accounts = this.accountDAO.get(accountId, false);
            if (accounts == null)
                throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.NOT_FOUND);
            return this.accountDAO.softDelete(accounts);
        }
    }

    @Override
    @Transactional
    public Boolean updateAccount(Accounts accounts, String[] allowedRoles) {
        accounts.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        return this.accountDAO.update(accounts);
    }

    @Override
    @Transactional
    public Accounts getAccount(Long accountId, boolean su) {
        if (su) {
            return this.accountDAO.get(Accounts.class, accountId);
        } else {
            return this.accountDAO.get(accountId, false);
        }
    }

    @Override
    @Transactional
    public Accounts findAccountByEmail(String email, boolean isDeleted) {
        Accounts accounts = this.accountDAO.getItemByColumn("email", email, isDeleted);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.NOT_FOUND);

        return accounts;
    }

    @Override
    @Transactional
    public Accounts findAccountByUsername(String userName, boolean isDeleted) {
        Accounts accounts = this.accountDAO.getItemByColumn("userName", userName, isDeleted);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT, HttpStatus.NOT_FOUND);

        return accounts;
    }

    @Override
    @Transactional
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        } else {
            return this.accountDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.accountDAO.searchBy(Accounts.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}