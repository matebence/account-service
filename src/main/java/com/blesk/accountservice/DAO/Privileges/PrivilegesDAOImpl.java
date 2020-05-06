package com.blesk.accountservice.DAO.Privileges;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class PrivilegesDAOImpl extends DAOImpl<Privileges> implements PrivilegesDAO {

    @Override
    public Boolean update(Privileges privileges) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(privileges);
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
    public Boolean softDelete(Privileges privileges) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(privileges);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Privileges get(Long id, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Privileges> criteriaQuery = criteriaBuilder.createQuery(Privileges.class);
        Root<Privileges> root = criteriaQuery.from(Privileges.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("privilegeId"), id), criteriaBuilder.equal(root.get("isDeleted"), isDeleted)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Privileges> getAll(int pageNumber, int pageSize, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(Privileges.class)));
        Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageSize > total)
            pageSize = total.intValue();

        if ((pageNumber > 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber == 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber > 0) && (pageNumber == Math.floor(total / pageSize)) ||
                (pageNumber == 0) && (pageNumber == Math.floor(total / pageSize))) {

            CriteriaQuery<Privileges> criteriaQuery = criteriaBuilder.createQuery(Privileges.class);
            Root<Privileges> select = criteriaQuery.from(Privileges.class);
            CriteriaQuery<Privileges> entity = criteriaQuery.select(select).where(criteriaBuilder.equal(select.get("isDeleted"), isDeleted)).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Privileges> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            try {
                return typedQuery.getResultList();
            } catch (Exception ex) {
                session.clear();
                session.close();
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Privileges getItemByColumn(String column, String value, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Privileges> criteriaQuery = criteriaBuilder.createQuery(Privileges.class);
        Root<Privileges> root = criteriaQuery.from(Privileges.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(column), value), criteriaBuilder.equal(root.get("isDeleted"), isDeleted)))).getSingleResult();
        } catch (Exception ex) {
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
        CriteriaQuery<Privileges> criteriaQuery = criteriaBuilder.createQuery(Privileges.class);
        Root<Privileges> root = criteriaQuery.from(Privileges.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
        CriteriaQuery<Privileges> select = criteriaQuery.select(root);

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

        TypedQuery<Privileges> typedQuery = session.createQuery(select);
        if (criterias.get(Keys.PAGINATION) != null) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(PAGE_SIZE);

            HashMap<String, Object> map = new HashMap<>();
            List<Privileges> result = typedQuery.getResultList();

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
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}