package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.DAO.Accounts.AccountsDAOImpl;
import com.blesk.accountservice.DAO.Passwords.PasswordsDAOImpl;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Service.Emails.EmailsServiceImpl;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.MethodInvocationException.ERROR_CODE;

@Service
public class PasswordsServiceImpl implements PasswordsService {

    @Value("${blesk.javamailer.url.forget-password}")
    private String resetPasswordUrl;

    @Value("${blesk.javamailer.url.application-login}")
    private String applicationLoginUrl;

    private PasswordsDAOImpl passwordsDAO;

    private AccountsDAOImpl accountsDAO;

    private PasswordEncoder passwordEncoder;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PasswordsServiceImpl(PasswordsDAOImpl passwordsDAO, AccountsDAOImpl accountsDAO, PasswordEncoder passwordEncoder, EmailsServiceImpl emailsService) {
        this.passwordsDAO = passwordsDAO;
        this.accountsDAO = accountsDAO;
        this.passwordEncoder = passwordEncoder;
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
    public Boolean deletePasswordToken(Passwords passwords) {
        return this.passwordsDAO.delete(passwords);
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
        return this.passwordsDAO.get(Passwords.class, "passwordTokenId", passwordTokenId);
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

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean generateNewPassword(long accountId) {
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(1);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(1);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(1);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(1);

        String password = passwordGenerator.generatePassword(8, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
        Accounts accounts = this.accountsDAO.get(Accounts.class, "accountId", accountId);
        accounts.setPassword(this.passwordEncoder.encode(password));
        if (!this.accountsDAO.update(accounts)) return Boolean.FALSE;

        Map<String, Object> variables = new HashMap<>();
        variables.put("newPassword", password);
        variables.put("applicationUrl", this.applicationLoginUrl);
        this.emailsService.sendHtmlMesseage("Nové heslo", "verifiedpassword", variables, accounts);
        return Boolean.TRUE;
    }
}