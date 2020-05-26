package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class PreferencesDAOImpl extends DAOImpl<Preferences> implements PreferencesDAO {

    @Override
    public Boolean update(Preferences preferences) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(preferences);
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
    public Preferences get(Long id) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("preferenceId"), id), criteriaBuilder.equal(root.get("isDeleted"), false)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Preferences> getAll(int pageNumber, int pageSize) {
        Session session = this.entityManager.unwrap(Session.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        try {
            CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
            countCriteria.select(criteriaBuilder.count(countCriteria.from(Preferences.class)));
            Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

            CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
            Root<Preferences> select = criteriaQuery.from(Preferences.class);
            CriteriaQuery<Preferences> entity = criteriaQuery.select(select).where(criteriaBuilder.equal(select.get("isDeleted"), false)).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Preferences> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            return new PageImpl<Preferences>(typedQuery.getResultList(), pageable, total).getContent();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public Preferences getItemByColumn(String column, String value) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
        Root<Preferences> root = criteriaQuery.from(Preferences.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(column), value), criteriaBuilder.equal(root.get("isDeleted"), false)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        HashMap<String, Object> map = new HashMap<>(); PageImpl page = null;

        try {
            CriteriaQuery<Preferences> criteriaQuery = criteriaBuilder.createQuery(Preferences.class);
            Root<Preferences> root = criteriaQuery.from(Preferences.class);
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
            CriteriaQuery<Preferences> select = criteriaQuery.select(root);

            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
                }
                select.where(predicates.toArray(new Predicate[]{}));
            }
            if (criterias.get(Keys.ORDER_BY) != null) {
                List<Order> orderList = new ArrayList();

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
            if (criterias.get(Keys.PAGINATION) != null) {
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                TypedQuery<Preferences> typedQuery = session.createQuery(select);
                long total = typedQuery.getResultList().size();
                typedQuery.setFirstResult(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
                typedQuery.setMaxResults(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                page = new PageImpl<Preferences>(typedQuery.getResultList(), pageable, total);

                map.put("hasPrev", page.getNumber() > 0);
                map.put("hasNext", page.getNumber() < total - 1);
            }

            if (page == null) return null;
            map.put("results", page.getContent());
            return map;
        } catch (Exception e) {
            return Collections.<String, Object>emptyMap();
        }
    }
}