package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Value.Keys;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class AccountsDAOImpl extends DAOImpl<Accounts> implements AccountsDAO {

    @Override
    public Accounts getAccountInformations(String userName) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);
        try {
            return session.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("userName"), userName))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Accounts findAccountByEmail(String email) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);
        try {
            return session.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("email"), email))).getSingleResult();
        } catch (NoResultException ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber) {
        final int PAGE_SIZE = 10;
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Accounts> criteriaQuery = criteriaBuilder.createQuery(Accounts.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaQuery<Accounts> select = criteriaQuery.select(root);

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
            List<Accounts> accounts = typedQuery.getResultList();

            int total = accounts.size();

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

            map.put("results", accounts);
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