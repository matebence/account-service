package com.blesk.accountservice.DAO.Activations;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Activations;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ActivationsDAOImpl extends DAOImpl<Activations> implements ActivationsDAO {

    @Override
    public Activations gettActivationToken(String token) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Activations> criteriaQuery = criteriaBuilder.createQuery(Activations.class);
        Root<Activations> root = criteriaQuery.from(Activations.class);
        try {
            return session.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("token"), token))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}