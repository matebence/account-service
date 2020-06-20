package com.blesk.accountservice.DAO.Passwords;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Passwords;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordsDAOImpl extends DAOImpl<Passwords> implements PasswordsDAO {

    @Override
    public Boolean softDelete(Passwords passwords) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            passwords = this.entityManager.contains(passwords) ? passwords : this.entityManager.merge(passwords);
            session.delete(passwords);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }
}