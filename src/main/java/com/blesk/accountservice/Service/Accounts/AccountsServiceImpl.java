package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
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

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Accounts createAccount(@Validated(Accounts.validationWithoutEncryption.class) Accounts accounts, JwtMapper jwtMapper) {
        accounts.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        accounts.setActivations(new Activations(UUID.randomUUID().toString()));
        Accounts account = this.accountDAO.save(accounts);
        if (account == null)
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);
        return account;
    }

    @Override
    @Transactional
    public Boolean softDeleteAccount(Long accountId, JwtMapper jwtMapper) {
        Accounts accounts = this.accountDAO.get(accountId, false);
        if (accounts == null)
            throw new AccountServiceException(Messages.DELETE_GET_ACCOUNT);
        if (!this.accountDAO.softDelete(accounts))
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteAccount(Long accountId) {
        if (!this.accountDAO.delete("accounts", "account_id", accountId))
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateAccount(Accounts accounts, JwtMapper jwtMapper) {
        if (!this.accountDAO.update(accounts))
            throw new AccountServiceException(Messages.UPDATE_ACCOUNT);
        return true;
    }

    @Override
    @Transactional
    public Accounts getAccount(Long accountId, boolean isDeleted) {
        Accounts accounts = this.accountDAO.get(accountId, isDeleted);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);
        return accounts;
    }

    @Override
    @Transactional
    public Accounts findAccountByEmail(String email, boolean isDeleted) {
        Accounts accounts = this.accountDAO.getItemByColumn("email", email, isDeleted);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);

        return accounts;
    }

    @Override
    @Transactional
    public Accounts findAccountByUsername(String userName, boolean isDeleted) {
        Accounts accounts = this.accountDAO.getItemByColumn("userName", userName, isDeleted);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);

        return accounts;
    }

    @Override
    @Transactional
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean isDeleted) {
        List<Accounts> accounts = this.accountDAO.getAll(pageNumber, pageSize, isDeleted);
        if (accounts.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS);
        return accounts;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> accounts = this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), isDeleted);

        if (accounts == null || accounts.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return accounts;
    }
}