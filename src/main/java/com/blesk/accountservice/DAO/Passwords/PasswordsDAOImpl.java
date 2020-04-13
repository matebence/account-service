package com.blesk.accountservice.DAO.Passwords;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Passwords;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class PasswordsDAOImpl extends DAOImpl<Passwords> implements PasswordsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Passwords getPasswordToken(String token) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Passwords> criteriaQuery = criteriaBuilder.createQuery(Passwords.class);
        Root<Passwords> root = criteriaQuery.from(Passwords.class);
        try {
            return this.entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("token"), token))).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}