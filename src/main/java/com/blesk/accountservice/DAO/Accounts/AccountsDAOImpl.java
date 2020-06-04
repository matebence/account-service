package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Accounts;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsDAOImpl extends DAOImpl<Accounts> implements AccountsDAO {

    @Override
    public Boolean update(Accounts accounts) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(accounts);
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