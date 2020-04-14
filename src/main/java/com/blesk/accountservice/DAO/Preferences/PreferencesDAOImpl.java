package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class PreferencesDAOImpl extends DAOImpl<AccountPreferenceItems> implements PreferencesDAO {

    private EntityManager entityManager;

    @Autowired
    public PreferencesDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public AccountPreferenceItems getPreferenceByName(String name) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AccountPreferenceItems> criteriaQuery = criteriaBuilder.createQuery(AccountPreferenceItems.class);
        Root<AccountPreferenceItems> root = criteriaQuery.from(AccountPreferenceItems.class);
        try {
            return session.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}