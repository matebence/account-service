package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exceptions.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Values.Criteria;
import com.blesk.accountservice.Values.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountsServiceImpl implements AccountsService {

    private AccountsDAOImpl accountDAO;

    private RolesDAOImpl roleDAO;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, RolesDAOImpl roleDAO) {
        this.accountDAO = accountDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public Accounts createAccount(Accounts accounts, ArrayList<String> roles) {
        Set<Roles> assignedRoles = this.roleDAO.getListOfRoles(roles);
        if (assignedRoles.isEmpty())
            throw new AccountServiceException(Messages.CREATE_GET_ACCOUNT);
        accounts.setRoles(assignedRoles);
        if (this.accountDAO.save(accounts).getAccountId() == null)
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);
        return accounts;
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AccountServiceException(Messages.DELETE_GET_ACCOUNT);
        if (!this.accountDAO.delete(accounts))
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    public boolean updateAccount(Accounts accounts) {
        if (!this.accountDAO.update(accounts))
            throw new AccountServiceException(Messages.UPDATE_ACCOUNT);
        return true;
    }

    @Override
    public Accounts getAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT);
        return accounts;
    }

    @Override
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize) {
        List<Accounts> accounts = this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        if (accounts.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS);
        return accounts;
    }

    @Override
    public Accounts getAccountInformations(String userName) {
        Accounts accounts = this.accountDAO.getAccountInformations(userName);
        if (accounts == null)
            throw new AccountServiceException(Messages.GET_ACCOUNT_INFORMATION);
        if (accounts.getRoles().isEmpty()) {
            throw new AccountServiceException(Messages.GET_ROLES_TO_ACCOUNT);
        }

        return accounts;
    }

    @Override
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Criteria.PAGINATION) == null)
            throw new NullPointerException(Messages.PAGINATION_EXCEPTION);

        Map<String, Object> accounts = this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Criteria.PAGINATION).get(Criteria.PAGE_NUMBER)));

        if (accounts.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_FOR_ACCOUNT);

        return accounts;
    }
}