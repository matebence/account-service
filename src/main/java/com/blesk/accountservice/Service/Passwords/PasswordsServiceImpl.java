package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.DAO.Passwords.PasswordsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PasswordsServiceImpl implements PasswordsService {

    private PasswordsDAOImpl passwordsDAO;

    @Autowired
    public PasswordsServiceImpl(PasswordsDAOImpl passwordsDAO) {
        this.passwordsDAO = passwordsDAO;
    }

    @Override
    @Transactional
    public Passwords createPasswordToken(Passwords passwords) {
        Passwords password = this.passwordsDAO.save(passwords);
        if (password == null)
            throw new AccountServiceException(Messages.CREATE_PASSWORD_TOKEN);
        return password;
    }

    @Override
    @Transactional
    public Boolean deletePasswordToken(Long passwordTokenId) {
        Passwords passwords = this.passwordsDAO.get(Passwords.class, passwordTokenId);
        if (passwords == null)
            throw new AccountServiceException(Messages.GET_PASSWORD_TOKEN);
        if (!this.passwordsDAO.delete("passwords", "password_id", passwordTokenId))
            throw new AccountServiceException(Messages.DELETE_PASSWORD_TOKEN);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePasswordToken(Passwords passwords) {
        if (!this.passwordsDAO.update(passwords))
            throw new AccountServiceException(Messages.UPDATE_PASSWORD_TOKEN);
        return true;
    }

    @Override
    @Transactional
    public Passwords getPasswordToken(Long passwordTokenId) {
        Passwords passwords = this.passwordsDAO.get(Passwords.class, passwordTokenId);
        if (passwords == null)
            throw new AccountServiceException(Messages.GET_PASSWORD_TOKEN);
        return passwords;
    }

    @Override
    @Transactional
    public Passwords findPasswordToken(String token) {
        Passwords passwords = this.passwordsDAO.getItemByColumn(Passwords.class, "token", token);
        if (passwords == null)
            throw new AccountServiceException(Messages.GET_PASSWORD_TOKEN);

        return passwords;
    }

    @Override
    @Transactional
    public List<Passwords> getAllPasswordTokens(int pageNumber, int pageSize) {
        List<Passwords> passwords = this.passwordsDAO.getAll(Passwords.class, pageNumber, pageSize);
        if (passwords.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_PASSWORD_TOKENS);
        return passwords;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPasswordToken(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> passwords = this.passwordsDAO.searchBy(Passwords.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (passwords == null || passwords.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return passwords;
    }

    @Override
    @Transactional
    public Boolean validatePasswordToken(long accountId, String token) {
        Passwords passwords = this.passwordsDAO.getItemByColumn(Passwords.class, "token", token);
        if (passwords == null)
            throw new AccountServiceException(Messages.VALIDATE_PASSWORD_TOKEN);

        if (passwords.getAccounts().getAccountId() != accountId)
            return false;

        Calendar cal = Calendar.getInstance();
        return (passwords.getExpiryDate().getTime() - cal.getTime().getTime()) > 0;
    }
}