package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Preferences;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class PreferencesDAOImpl extends DAOImpl<Preferences> implements PreferencesDAO {

    @Override
    public Boolean update(Preferences preferences) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(preferences);
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