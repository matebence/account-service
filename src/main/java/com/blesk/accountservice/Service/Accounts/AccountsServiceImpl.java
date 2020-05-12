package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.Model.AccountRoles;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Value.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.LockModeType;
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
    @Lock(value = LockModeType.WRITE)
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
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteAccount(Accounts accounts, boolean su) {
        if (su) {
            return this.accountDAO.delete("accounts", "account_id", accounts.getAccountId());
        } else {
            return this.accountDAO.softDelete(accounts);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateAccount(Accounts accounts, String[] allowedRoles) {
        accounts.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        return this.accountDAO.update(accounts);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts getAccount(Long accountId, boolean su) {
        if (su) {
            return this.accountDAO.get(Accounts.class, accountId);
        } else {
            return this.accountDAO.get(accountId, false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByEmail(String email, boolean isDeleted) {
        return this.accountDAO.getItemByColumn("email", email, isDeleted);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByUsername(String userName, boolean isDeleted) {
        return this.accountDAO.getItemByColumn("userName", userName, isDeleted);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        } else {
            return this.accountDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Accounts> getAccountsForJoin(List<Long> ids, String columName) {
        return this.accountDAO.getJoinValuesByColumn(Accounts.class, ids, columName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.accountDAO.searchBy(Accounts.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}