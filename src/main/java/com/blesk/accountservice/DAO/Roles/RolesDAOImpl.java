package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

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

    @Override
    public Boolean softDelete(Roles roles) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(roles);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Roles get(Long id, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("roleId"), id), criteriaBuilder.equal(root.get("isDeleted"), isDeleted)))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Roles> getAll(int pageNumber, int pageSize, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(Roles.class)));
        Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageSize > total)
            pageSize = total.intValue();

        if ((pageNumber > 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber == 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber > 0) && (pageNumber == Math.floor(total / pageSize)) ||
                (pageNumber == 0) && (pageNumber == Math.floor(total / pageSize))) {

            CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
            Root<Roles> select = criteriaQuery.from(Roles.class);
            CriteriaQuery<Roles> entity = criteriaQuery.select(select).where(criteriaBuilder.equal(select.get("isDeleted"), isDeleted)).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Roles> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            try {
                return typedQuery.getResultList();
            } catch (NoResultException ex) {
                session.clear();
                session.close();
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Roles getItemByColumn(String column, String value, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(column), value), criteriaBuilder.equal(root.get("isDeleted"), isDeleted)))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted) {
        final int PAGE_SIZE = 10;
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
        CriteriaQuery<Roles> select = criteriaQuery.select(root);

        if (criterias.get(Keys.ORDER_BY) != null) {
            List<Order> orderList = new ArrayList<Order>();

            for (Object o : criterias.get(Keys.ORDER_BY).entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                if (pair.getValue().toString().toLowerCase().equals("asc")) {
                    orderList.add(criteriaBuilder.asc(root.get(pair.getKey().toString())));
                } else if (pair.getValue().toString().toLowerCase().equals("desc")) {
                    orderList.add(criteriaBuilder.desc(root.get(pair.getKey().toString())));
                }
            }
            select.orderBy(orderList);
        }

        if (criterias.get(Keys.SEARCH) != null) {
            for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
            }
            select.where(predicates.toArray(new Predicate[]{}));
        }

        TypedQuery<Roles> typedQuery = session.createQuery(select);
        if (criterias.get(Keys.PAGINATION) != null) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(PAGE_SIZE);

            HashMap<String, Object> map = new HashMap<>();
            List<Roles> result = typedQuery.getResultList();

            int total = result.size();

            if ((pageNumber > 0) && (pageNumber < (Math.floor(total / PAGE_SIZE)))) {
                map.put("hasPrev", true);
                map.put("hasNext", true);
            } else if ((pageNumber == 0) && (pageNumber < (Math.floor(total / PAGE_SIZE)))) {
                map.put("hasPrev", false);
                map.put("hasNext", true);
            } else if ((pageNumber > 0) && (pageNumber == Math.floor(total / PAGE_SIZE))) {
                map.put("hasPrev", true);
                map.put("hasNext", false);
            } else if ((pageNumber == 0) && (pageNumber == Math.floor(total / PAGE_SIZE))) {
                map.put("hasPrev", false);
                map.put("hasNext", false);
            } else {
                return Collections.<String, Object>emptyMap();
            }

            map.put("results", result);
            return map;
        }

        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("results", typedQuery.getResultList());
            return map;
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}