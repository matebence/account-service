package com.blesk.accountservice.DAO.Logins;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Logins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class LoginsDAOImpl extends DAOImpl<Logins> implements LoginsDAO {

    private EntityManager entityManager;

    @Autowired
    public LoginsDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }
}
