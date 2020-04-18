package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class AccountsDAOImpl extends DAOImpl<Accounts> implements AccountsDAO {

    @Override
    public Boolean softDelete(Accounts accounts) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(accounts);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Accounts get(Long id, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedAccountFilter");
        filter.setParameter("isDeleted", isDeleted);

        try {
            Accounts accounts = (Accounts) session.get(Accounts.class, id);
            session.disableFilter("deletedAccountFilter");
            return accounts;
        } catch (Exception e) {
            session.disableFilter("deletedAccountFilter");
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Accounts> getAll(int pageNumber, int pageSize, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedAccountFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(Accounts.class)));
        Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageSize > total)
            pageSize = total.intValue();

        if ((pageNumber > 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber == 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber > 0) && (pageNumber == Math.floor(total / pageSize)) ||
                (pageNumber == 0) && (pageNumber == Math.floor(total / pageSize))) {

            CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
            Root<Accounts> select = criteriaQuery.from(Accounts.class);
            CriteriaQuery<Accounts> entity = criteriaQuery.select(select).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Accounts> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            try {
                List<Accounts> list = typedQuery.getResultList();
                session.disableFilter("deletedAccountFilter");
                return list;
            } catch (NoResultException ex) {
                session.disableFilter("deletedAccountFilter");
                session.clear();
                session.close();
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Accounts getItemByColumn(String column, String value, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedAccountFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);

        try {
            Accounts accounts = session.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get(column), value))).getSingleResult();
            session.disableFilter("deletedAccountFilter");
            return accounts;
        } catch (NoResultException ex) {
            session.disableFilter("deletedAccountFilter");
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted) {
        final int PAGE_SIZE = 10;
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedAccountFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaQuery<Accounts> select = criteriaQuery.select(root);

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
                predicates.add(criteriaBuilder.like(root.get(pair.getKey().toString()), "%" + pair.getValue().toString() + "%"));
            }
            select.where(predicates.toArray(new Predicate[]{}));
        }

        TypedQuery<Accounts> typedQuery = session.createQuery(select);
        if (criterias.get(Keys.PAGINATION) != null) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(PAGE_SIZE);

            HashMap<String, Object> map = new HashMap<>();
            List<Accounts> result = typedQuery.getResultList();

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
            session.disableFilter("deletedAccountFilter");
            return map;
        }

        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("results", typedQuery.getResultList());
            session.disableFilter("deletedAccountFilter");
            return map;
        } catch (NoResultException ex) {
            session.disableFilter("deletedAccountFilter");
            session.clear();
            session.close();
            return null;
        }
    }
}