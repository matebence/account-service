package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.DAO.Passwords.PasswordsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Passwords;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;

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
        if (this.passwordsDAO.save(passwords).getPasswordResetTokenId() == null)
            throw new AccountServiceException(Messages.CREATE_PASSWORD_TOKEN);
        return passwords;
    }

    @Override
    @Transactional
    public Boolean deletePasswordToken(Long passwordResetTokenId) {
        Passwords passwords = this.passwordsDAO.get(Passwords.class, passwordResetTokenId);
        if (passwords == null)
            throw new AccountServiceException(Messages.DELETE_GET_PASSWORD_TOKEN);
        if (!this.passwordsDAO.delete(passwords))
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
    public Passwords getPasswordToken(Long passwordResetTokenId) {
        Passwords passwords = this.passwordsDAO.get(Passwords.class, passwordResetTokenId);
        if (passwords == null)
            throw new AccountServiceException(Messages.GET_PASSWORD_TOKEN);
        return passwords;
    }

    @Override
    @Transactional
    public Boolean validatePasswordResetToken(long accountId, String token) {
        Passwords passwords = this.passwordsDAO.getPasswordTokenByToken(token);
        if (passwords == null)
            throw new AccountServiceException(Messages.VALIDATE_PASSWORD_TOKEN);

        if (passwords.getAccount().getAccountId() != accountId)
            return false;

        Calendar cal = Calendar.getInstance();
        return (passwords.getExpiryDate().getTime() - cal.getTime().getTime()) > 0;
    }
}