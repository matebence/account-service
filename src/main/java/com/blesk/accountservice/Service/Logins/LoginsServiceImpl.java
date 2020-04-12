package com.blesk.accountservice.Service.Logins;

import com.blesk.accountservice.DAO.Logins.LoginsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginsServiceImpl implements LoginsService {

    private LoginsDAOImpl loginsDAO;

    @Autowired
    public LoginsServiceImpl(LoginsDAOImpl loginsDAO) {
        this.loginsDAO = loginsDAO;
    }

    @Override
    @Transactional
    public Logins createLogin(Logins logins) {
        if (this.loginsDAO.save(logins).getLoginId() == null)
            throw new AccountServiceException(Messages.CREATE_LOGIN);
        return logins;
    }

    @Override
    @Transactional
    public Boolean deleteLogin(Long loginId) {
        Logins logins = this.loginsDAO.get(Logins.class, loginId);
        if (logins == null)
            throw new AccountServiceException(Messages.DELETE_GET_LOGIN);
        if (!this.loginsDAO.delete(logins))
            throw new AccountServiceException(Messages.DELETE_LOGIN);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateLogin(Logins logins) {
        if (!this.loginsDAO.update(logins))
            throw new AccountServiceException(Messages.UPDATE_LOGIN);
        return true;
    }

    @Override
    @Transactional
    public Logins getLogin(Long loginId) {
        Logins login = this.loginsDAO.get(Logins.class, loginId);
        if (login == null)
            throw new AccountServiceException(Messages.GET_LOGIN);
        return login;
    }
}
