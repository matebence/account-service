package com.blesk.accountservice.Service.Logins;

import com.blesk.accountservice.DAO.Logins.LoginsDAOImpl;
import com.blesk.accountservice.Model.Logins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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
    @Lock(value = LockModeType.WRITE)
    public Logins createLogin(Logins logins) {
        return this.loginsDAO.save(logins);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteLogin(Logins logins) {
        return this.loginsDAO.delete(logins);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateLogin(Logins logins) {
        return this.loginsDAO.update(logins);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Logins findLoginByIpAddress(String ipAddress) {
        return this.loginsDAO.getItemByColumn(Logins.class, "ipAddress", ipAddress);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Logins> getAllLogins(int pageNumber, int pageSize) {
        return this.loginsDAO.getAll(Logins.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForLogin(HashMap<String, HashMap<String, String>> criterias) {
        return this.loginsDAO.searchBy(Logins.class, criterias);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Logins getLogin(Long loginId) {
        return this.loginsDAO.get(Logins.class, "loginId", loginId);
    }
}