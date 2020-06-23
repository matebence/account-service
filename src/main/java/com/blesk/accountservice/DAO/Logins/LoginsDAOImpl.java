package com.blesk.accountservice.DAO.Logins;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Logins;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class LoginsDAOImpl extends DAOImpl<Logins> implements LoginsDAO {

    @Override
    public Boolean update(Logins logins) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.saveOrUpdate(logins);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }
}