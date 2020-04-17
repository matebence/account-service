package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.AccountRoleItems.AccountRoles;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
public class AccountsServiceImpl implements AccountsService {

    private AccountsDAOImpl accountDAO;

    private RolesDAOImpl rolesDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, RolesDAOImpl rolesDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.rolesDAO = rolesDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Accounts createAccount(@Validated(Accounts.validationWithoutEncryption.class) Accounts accounts) {
        Accounts account = this.accountDAO.save(accounts);
        if (account == null)
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);
        return account;
    }

    @Override
    @Transactional
    public Boolean deleteAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AccountServiceException(Messages.DELETE_GET_ACCOUNT);
        if (!this.accountDAO.delete(accounts))
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateAccount(Accounts accounts) {
        if (!this.accountDAO.update(accounts))
            throw new AccountServiceException(Messages.UPDATE_ACCOUNT);
        return true;
    }

    @Override
    @Transactional
    public Accounts getAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);
        return accounts;
    }

    @Override
    @Transactional
    public Accounts findAccountByEmail(String email) {
        Accounts accounts = this.accountDAO.getItemByColumn(Accounts.class, "email", email);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);

        return accounts;
    }

    @Override
    @Transactional
    public Accounts findAccountByUsername(String userName) {
        Accounts accounts = this.accountDAO.getItemByColumn(Accounts.class, "userName", userName);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);

        return accounts;
    }

    @Override
    @Transactional
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize) {
        List<Accounts> accounts = this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        if (accounts.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS);
        return accounts;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> accounts = this.accountDAO.searchBy(Accounts.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (accounts == null || accounts.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return accounts;
    }
}