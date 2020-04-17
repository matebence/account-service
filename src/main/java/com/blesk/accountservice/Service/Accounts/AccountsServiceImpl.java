package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DAO.Activations.ActivationsDAOImpl;
import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
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

    private RolesDAOImpl roleDAO;

    private ActivationsDAOImpl activationsDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, RolesDAOImpl roleDAO, ActivationsDAOImpl activationsDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.roleDAO = roleDAO;
        this.activationsDAO = activationsDAO;
        this.passwordEncoder = passwordEncoder;
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
    @Transactional
    public Activations createAccount(@Validated(Accounts.validationWithoutEncryption.class) Accounts accounts, String[] allowedRoles) {
        Set<Roles> roles = new HashSet<>(accounts.getRoles());
        if (!checkForAllowedRoles(roles, allowedRoles))
            throw new AccountServiceException(Messages.ACCOUNT_NEW_ERROR);
        Set<Roles> assignedRoles = this.roleDAO.getListOfRoles(accounts.getRoles());
        if (assignedRoles.isEmpty())
            throw new AccountServiceException(Messages.CREATE_GET_ACCOUNT);

        accounts.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        accounts.setRoles(assignedRoles);
        Activations activations = new Activations(UUID.randomUUID().toString());
        accounts.setActivations(activations);
        Accounts account = this.accountDAO.save(accounts);

        if (account == null)
            throw new AccountServiceException(Messages.CREATE_ACCOUNT);

        activations.setAccounts(account);
        Activations activation = this.activationsDAO.save(activations);

        if (activation == null)
            throw new AccountServiceException(Messages.ACTIVATION_TOKEN_ACCOUNT);

        return activation;
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
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize) {
        List<Accounts> accounts = this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        if (accounts.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_ACCOUNTS);
        return accounts;
    }

    @Override
    @Transactional
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
    @Transactional
    public Accounts findAccountByEmail(String email) {
        Accounts accounts = this.accountDAO.findAccountByEmail(email);
        if (accounts == null)
            throw new AccountServiceException(Messages.FIND_ACCOUNT_BY_EMAIL);

        return accounts;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_EXCEPTION);

        Map<String, Object> accounts = this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (accounts == null || accounts.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_FOR_ACCOUNT);

        return accounts;
    }
}