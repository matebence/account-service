package com.blesk.authorizationserver.Service.Accounts;

import com.blesk.authorizationserver.DAO.Accounts.AccountsDAOImpl;
import com.blesk.authorizationserver.DAO.Roles.RolesDAOImpl;
import com.blesk.authorizationserver.DTO.JwtResponse;
import com.blesk.authorizationserver.Exceptions.AuthorizationServerException;
import com.blesk.authorizationserver.Listeners.LoginAttemptService;
import com.blesk.authorizationserver.Model.Accounts;
import com.blesk.authorizationserver.Model.Privileges;
import com.blesk.authorizationserver.Model.Roles;
import com.blesk.authorizationserver.Values.Criteria;
import com.blesk.authorizationserver.Values.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccountsServiceImpl implements AccountsService, UserDetailsService {

    @Value("${config.oauth2.max-no-try}")
    private String maxNoTrys;

    private AccountsDAOImpl accountDAO;

    private RolesDAOImpl roleDAO;

    private LoginAttemptService loginAttemptService;

    private HttpServletRequest request;

    @Autowired
    public AccountsServiceImpl(AccountsDAOImpl accountDAO, RolesDAOImpl roleDAO, LoginAttemptService loginAttemptService, HttpServletRequest request) {
        this.accountDAO = accountDAO;
        this.roleDAO = roleDAO;
        this.loginAttemptService = loginAttemptService;
        this.request = request;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public Accounts createAccount(Accounts accounts, ArrayList<String> roles) {
        Set<Roles> assignedRoles = this.roleDAO.getListOfRoles(roles);
        if (assignedRoles.isEmpty())
            throw new AuthorizationServerException(Messages.CREATE_GET_ACCOUNT);
        accounts.setRoles(assignedRoles);
        if (this.accountDAO.save(accounts).getAccountId() == null)
            throw new AuthorizationServerException(Messages.CREATE_ACCOUNT);
        return accounts;
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AuthorizationServerException(Messages.DELETE_GET_ACCOUNT);
        if (!this.accountDAO.delete(accounts))
            throw new AuthorizationServerException(Messages.DELETE_ACCOUNT);
        return true;
    }

    @Override
    public boolean updateAccount(Accounts accounts) {
        if (!this.accountDAO.update(accounts))
            throw new AuthorizationServerException(Messages.UPDATE_ACCOUNT);
        return true;
    }

    @Override
    public Accounts getAccount(Long accountId) {
        Accounts accounts = this.accountDAO.get(Accounts.class, accountId);
        if (accounts == null)
            throw new AuthorizationServerException(Messages.GET_ACCOUNT);
        return accounts;
    }

    @Override
    public List<Accounts> getAllAccounts(int pageNumber, int pageSize) {
        List<Accounts> accounts = this.accountDAO.getAll(Accounts.class, pageNumber, pageSize);
        if (accounts.isEmpty())
            throw new AuthorizationServerException(Messages.GET_ALL_ACCOUNTS);
        return accounts;
    }

    @Override
    public Accounts getAccountInformations(String userName) {
        Accounts accounts = this.accountDAO.getAccountInformations(userName);
        if (accounts == null)
            throw new AuthorizationServerException(Messages.GET_ACCOUNT_INFORMATION);
        if (accounts.getRoles().isEmpty()) {
            throw new AuthorizationServerException(Messages.GET_ROLES_TO_ACCOUNT);
        }

        return accounts;
    }

    @Override
    public Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Criteria.PAGINATION) == null)
            throw new NullPointerException(Messages.PAGINATION_EXCEPTION);

        Map<String, Object> accounts = this.accountDAO.searchBy(criteria, Integer.parseInt(criteria.get(Criteria.PAGINATION).get(Criteria.PAGE_NUMBER)));

        if (accounts.isEmpty())
            throw new AuthorizationServerException(Messages.SEARCH_FOR_ACCOUNT);

        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new AuthorizationServerException(Messages.BLOCKED_EXCEPTION);
        }

        Accounts accounts = this.getAccountInformations(userName);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : accounts.getRoles()) {
            for (Privileges privilege : role.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        accounts.setGrantedAuthorities(authorities);

        return new JwtResponse(accounts);
    }
}