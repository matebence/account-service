package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class PreferencesDAOImpl extends DAOImpl<Preferences> implements PreferencesDAO {

    @Override
    public Boolean softDelete(Preferences preferences) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(preferences);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Preferences get(Long id, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPreferenceFilter");
        filter.setParameter("isDeleted", isDeleted);

        try {
            Preferences preferences = (Preferences) session.get(Preferences.class, id);
            session.disableFilter("deletedPreferenceFilter");
            return preferences;
        } catch (Exception e) {
            session.disableFilter("deletedPreferenceFilter");
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Preferences> getAll(int pageNumber, int pageSize, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPreferenceFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(Preferences.class)));
        Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageSize > total)
            pageSize = total.intValue();

        if ((pageNumber > 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber == 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber > 0) && (pageNumber == Math.floor(total / pageSize)) ||
                (pageNumber == 0) && (pageNumber == Math.floor(total / pageSize))) {

            CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
            Root<Preferences> select = criteriaQuery.from(Preferences.class);
            CriteriaQuery<Preferences> entity = criteriaQuery.select(select).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Preferences> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            try {
                List<Preferences> list = typedQuery.getResultList();
                session.disableFilter("deletedPreferenceFilter");
                return list;
            } catch (NoResultException ex) {
                session.disableFilter("deletedPreferenceFilter");
                session.clear();
                session.close();
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Preferences getItemByColumn(String column, String value, boolean isDeleted) {
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPreferenceFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);

        try {
            Preferences preferences = session.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get(column), value))).getSingleResult();
            session.disableFilter("deletedPreferenceFilter");
            return preferences;
        } catch (NoResultException ex) {
            session.disableFilter("deletedPreferenceFilter");
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted) {
        final int PAGE_SIZE = 10;
        Session session = this.entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPreferenceFilter");
        filter.setParameter("isDeleted", isDeleted);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaQuery<Preferences> select = criteriaQuery.select(root);

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

        TypedQuery<Preferences> typedQuery = session.createQuery(select);
        if (criterias.get(Keys.PAGINATION) != null) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(PAGE_SIZE);

            HashMap<String, Object> map = new HashMap<>();
            List<Preferences> result = typedQuery.getResultList();

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
            session.disableFilter("deletedPreferenceFilter");
            return map;
        }

        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("results", typedQuery.getResultList());
            session.disableFilter("deletedPreferenceFilter");
            return map;
        } catch (NoResultException ex) {
            session.disableFilter("deletedPreferenceFilter");
            session.clear();
            session.close();
            return null;
        }
    }
}