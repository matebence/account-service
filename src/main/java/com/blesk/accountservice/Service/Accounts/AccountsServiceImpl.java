package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
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

    private boolean checkForAllowedRoles(Set<Roles> roles, String[] allowedRoles) {
        Iterator<Roles> it = roles.iterator();
        while (it.hasNext()) {
            Roles role = it.next();
            for (String allowedRole : allowedRoles) {
                if (role.getName().equals(allowedRole)) {
                    it.remove();
                }
            }
        }
        return roles.isEmpty();
    }

    @Override
    public Accounts createAccount(Accounts accounts, String[] allowedRoles) {
        if (accounts.getRoles().size() > 5)
            throw new AccountServiceException(Messages.ACCOUNT_NEW_ERROR);
        Set<Roles> roles = new HashSet<>(accounts.getRoles());
        if (!checkForAllowedRoles(roles, allowedRoles))
            throw new AccountServiceException(Messages.ACCOUNT_NEW_ERROR);
        Set<Roles> assignedRoles = this.roleDAO.getListOfRoles(accounts.getRoles());
        if (assignedRoles.isEmpty())
            throw new AccountServiceException(Messages.CREATE_GET_ACCOUNT);
        accounts.setRoles(assignedRoles);
        if (this.accountDAO.save(accounts).getAccountId() == null)
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);
        return accounts;
    }

    @Override
    public Boolean deleteAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AccountServiceException(Messages.DELETE_GET_ACCOUNT);
        if (!this.accountDAO.delete(accounts))
            throw new AccountServiceException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    public Boolean updateAccount(Accounts accounts) {
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
    public Accounts findAccountByEmail(String email) {
        Accounts accounts = this.accountDAO.findAccountByEmail(email);
        if (accounts == null)
            throw new AccountServiceException(Messages.FIND_ACCOUNT_BY_EMAIL);

        return accounts;
    }

    @Override
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new NullPointerException(Messages.PAGINATION_EXCEPTION);

        Map<String, Object> accounts = this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (accounts == null || accounts.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_FOR_ACCOUNT);

        return accounts;
    }
}