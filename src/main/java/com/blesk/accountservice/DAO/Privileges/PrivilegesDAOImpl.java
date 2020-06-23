package com.blesk.accountservice.DAO.Privileges;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Privileges;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class PrivilegesDAOImpl extends DAOImpl<Privileges> implements PrivilegesDAO {

    @Override
    public Boolean update(Privileges privileges) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(privileges);
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