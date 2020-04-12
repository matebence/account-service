package com.blesk.accountservice.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class DAOImpl<T> implements DAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public T save(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.save(t);
        } catch (Exception e) {
            session.clear();
            session.close();
            return null;
        }
        return t;
    }

    @Override
    public Boolean update(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.saveOrUpdate(t);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(t);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public T get(Class c, Long id) {
        Session session = this.entityManager.unwrap(Session.class);
        return (T) session.get(c, id);
    }

    @Override
    public List getAll(Class c, int pageNumber, int pageSize) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(c)));
        Long count = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageNumber > Math.floor(count.intValue() / pageSize)) {
            return Collections.emptyList();
        }

        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(c);
        Root select = criteriaQuery.from(c);

        CriteriaQuery entity = criteriaQuery.select(select).orderBy(criteriaBuilder.asc(select.get("createdAt")));

        Query typedQuery = session.createQuery(entity);
        typedQuery.setFirstResult(pageNumber);
        typedQuery.setMaxResults(pageSize);

        try {
            return typedQuery.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}