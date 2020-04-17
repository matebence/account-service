package com.blesk.accountservice.Service.Logins;

import com.blesk.accountservice.DAO.Logins.LoginsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Logins;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Logins login = this.loginsDAO.save(logins);
        if (login == null)
            throw new AccountServiceException(Messages.CREATE_LOGIN);
        return login;
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
    public Logins findLoginByIpAddress(String ipAddress) {
        Logins logins = this.loginsDAO.getItemByColumn(Logins.class, "ipAddress", ipAddress);
        if (logins == null)
            throw new AccountServiceException(Messages.GET_LOGIN);

        return logins;
    }

    @Override
    @Transactional
    public List<Logins> getAllLogins(int pageNumber, int pageSize) {
        List<Logins> logins = this.loginsDAO.getAll(Logins.class, pageNumber, pageSize);
        if (logins.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_LOGINS);
        return logins;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForLogins(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> logins = this.loginsDAO.searchBy(Logins.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (logins == null || logins.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return logins;
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