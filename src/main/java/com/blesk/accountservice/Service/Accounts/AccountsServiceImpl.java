package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DTO.AccountsJoin;
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
import org.springframework.validation.annotation.Validated;

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
    public Accounts createAccount(@Validated(Accounts.basicValidation.class) Accounts accounts, String[] allowedRoles) {
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
    public Boolean deleteAccount(Accounts accounts) {
        return this.accountDAO.softDelete(accounts);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateAccount(Accounts account, @Validated(Accounts.basicValidation.class) Accounts accounts, String[] allowedRoles) {
        account.setUserName(accounts.getUserName());
        account.setEmail(accounts.getEmail());

        if (!accounts.getAccountRoles().isEmpty()) {
            account.removeAllRoles(new HashSet<>(account.getAccountRoles()));
            account.addAllRoles(accounts.getAccountRoles());
        }

        if (accounts.getPassword().length() <= 30 && !accounts.getPassword().substring(0, "$2a$10$".length()).contains("$2a$10$")){
            account.setPassword(this.passwordEncoder.encode(accounts.getPassword()));
        }

        return this.accountDAO.update(account);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts getAccount(Long accountId) {
        return this.accountDAO.get(Accounts.class, "accountId", accountId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByEmail(String email) {
        return this.accountDAO.getItemByColumn(Accounts.class, "email", email);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Accounts findAccountByUsername(String userName) {
        return this.accountDAO.getItemByColumn(Accounts.class, "userName", userName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize) {
        return this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<AccountsJoin> getAccountsForJoin(List<Long> ids, List<String> listedRoles, String columName) {
        return this.accountDAO.getJoinValuesByColumn(ids, listedRoles, columName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criterias) {
        return this.accountDAO.searchBy(Accounts.class, criterias);
    }
}