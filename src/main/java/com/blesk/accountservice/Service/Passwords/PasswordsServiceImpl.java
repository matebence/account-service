package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.DAO.Passwords.PasswordsDAOImpl;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Emails.EmailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PasswordsServiceImpl implements PasswordsService {

    @Value("${blesk.javamailer.url.forget-password}")
    private String resetPasswordUrl;

    private PasswordsDAOImpl passwordsDAO;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PasswordsServiceImpl(PasswordsDAOImpl passwordsDAO, EmailsServiceImpl emailsService) {
        this.passwordsDAO = passwordsDAO;
        this.emailsService = emailsService;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Passwords createPasswordToken(Passwords passwords) {
        Passwords password = this.passwordsDAO.save(passwords);

        Map<String, Object> variables = new HashMap<>();
        variables.put("resetPasswordUrl", String.format(this.resetPasswordUrl, passwords.getAccounts().getAccountId(), passwords.getToken()));
        this.emailsService.sendHtmlMesseage("Zabudnuté heslo", "forgetpassword", variables, passwords.getAccounts());
        return password;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePasswordToken(Long passwordTokenId) {
        Passwords passwords = this.passwordsDAO.get(Passwords.class, passwordTokenId);
        if (passwords == null) return false;
        return this.passwordsDAO.delete("passwords", "password_id", passwordTokenId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePasswordToken(Passwords passwords) {
        return this.passwordsDAO.update(passwords);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Passwords getPasswordToken(Long passwordTokenId) {
        return this.passwordsDAO.get(Passwords.class, passwordTokenId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Passwords findPasswordToken(String token) {
        return this.passwordsDAO.getItemByColumn(Passwords.class, "token", token);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Passwords> getAllPasswordTokens(int pageNumber, int pageSize) {
        return this.passwordsDAO.getAll(Passwords.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPasswordToken(HashMap<String, HashMap<String, String>> criterias) {
        return this.passwordsDAO.searchBy(Passwords.class, criterias);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Boolean validatePasswordToken(long accountId, String token) {
        Passwords passwords = this.passwordsDAO.getItemByColumn(Passwords.class, "token", token);
        if (passwords == null) return false;
        if (passwords.getAccounts().getAccountId() != accountId) return false;
        Calendar cal = Calendar.getInstance();
        return (passwords.getExpiryDate().getTime() - cal.getTime().getTime()) > 0;
    }
}