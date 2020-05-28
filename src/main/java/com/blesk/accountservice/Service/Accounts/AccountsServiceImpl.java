package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.Model.AccountRoles;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Service.Emails.EmailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.*;

@Service
public class AccountsServiceImpl implements AccountsService {

    @Value("${blesk.javamailer.url.account-activation}")
    private String activationUrl;

    private AccountsDAOImpl accountDAO;

    private PasswordEncoder passwordEncoder;

    private EmailsServiceImpl emailsService;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, PasswordEncoder passwordEncoder, EmailsServiceImpl emailsService) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
        this.emailsService = emailsService;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Accounts createAccount(Accounts accounts, String[] allowedRoles) {
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
        Accounts account = this.accountDAO.save(accounts);

        Map<String, Object> variables = new HashMap<>();
        variables.put("activationUrl", String.format(this.activationUrl, account.getAccountId(), account.getActivations().getToken()));
        this.emailsService.sendHtmlMesseage("Registrácia", "signupactivation", variables, account);
        return account;
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
    public Boolean updateAccount(Accounts account, Accounts accounts, String[] allowedRoles) {
        account.setUserName(accounts.getUserName());
        account.setEmail(accounts.getEmail());
        account.setPassword(this.passwordEncoder.encode(accounts.getPassword()));

        for (AccountRoles accountRole : account.getAccountRoles()) {
            for (AccountRoles accountRoles : accounts.getAccountRoles()) {
                if (accountRoles.getDeleted() == null) {
                    if (!Arrays.asList(allowedRoles).contains(accountRoles.getRoles().getName())) {
                        account.getAccountRoles().remove(accountRoles);
                    } else {
                        account.addRole(accountRoles);
                    }
                } else if (accountRoles.getDeleted()) {
                    account.removeRole(accountRole);
                } else {
                    if (!Arrays.asList(allowedRoles).contains(accountRoles.getRoles().getName())) {
                        account.getAccountRoles().remove(accountRoles);
                    } else {
                        accountRole.setRoles(accountRoles.getRoles());
                    }
                }
            }
        }

        return this.accountDAO.update(account);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts getAccount(Long accountId, boolean su) {
        if (su) {
            return this.accountDAO.get(Accounts.class, accountId);
        } else {
            return this.accountDAO.get(accountId);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByEmail(String email, boolean su) {
        if (su) {
            return this.accountDAO.getItemByColumn(Accounts.class, "email", email);
        } else {
            return this.accountDAO.getItemByColumn("email", email);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByUsername(String userName, boolean su) {
        if (su) {
            return this.accountDAO.getItemByColumn(Accounts.class, "userName", userName);
        } else {
            return this.accountDAO.getItemByColumn("userName", userName);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        } else {
            return this.accountDAO.getAll(pageNumber, pageSize);
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
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criterias, boolean su) {
        if (su) {
            return this.accountDAO.searchBy(Accounts.class, criterias);
        } else {
            return this.accountDAO.searchBy(criterias);
        }
    }
}