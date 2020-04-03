package com.blesk.authorizationserver.DAO.Preferences;

import com.blesk.authorizationserver.DAO.DAOImpl;
import com.blesk.authorizationserver.Model.Preferences.Preferences;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class PreferencesDAOImpl extends DAOImpl<Preferences> implements PreferencesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Preferences getPreferenceByName(String name) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);
        return this.entityManager.createQuery(criteriaQuery
                .where(criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
    }
}