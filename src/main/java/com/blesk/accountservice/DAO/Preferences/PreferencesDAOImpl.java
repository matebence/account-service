package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Preferences.Preferences;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class PreferencesDAOImpl extends DAOImpl<Preferences> implements PreferencesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Preferences getPreferenceByName(String name) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);
        try {
            return this.entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}