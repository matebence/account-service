package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Roles;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class RolesDAOImpl extends DAOImpl<Roles> implements RolesDAO {

    @Override
    public Boolean update(Roles roles) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(roles);
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