package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.DTO.PreferencesSearch;
import com.blesk.accountservice.Model.AccountPreferences;
import com.blesk.accountservice.Model.Accounts;
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
import java.util.regex.Pattern;

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
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        HashMap<String, Object> map = new HashMap<>(); PageImpl page = null;

        try {
            CriteriaQuery<PreferencesSearch> criteriaQuery = criteriaBuilder.createQuery(PreferencesSearch.class);
            Root<Preferences> root = criteriaQuery.from(Preferences.class);
            Join<Preferences, AccountPreferences> accountPreferences = root.join("accountPreferences");
            Join<AccountPreferences, Accounts> accounts = accountPreferences.join("accounts");
            CompoundSelection<PreferencesSearch> selection = criteriaBuilder.construct(PreferencesSearch.class, root.get("preferenceId"), root.get("name"), accountPreferences.get("content"), accountPreferences.get("isSet"), accountPreferences.get("value"), accounts.get("accountId"));

            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
            CriteriaQuery<PreferencesSearch> select = criteriaQuery.select(selection);

            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if(Pattern.compile("^[\\D][a-zA-Z0-9 ]*$").matcher(pair.getValue().toString()).find()){
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
                    }else{
                        if(pair.getKey().toString().equals("accountId")){
                            predicates.add(criteriaBuilder.equal(accounts.get(pair.getKey().toString()), pair.getValue().toString()));
                        } else if (pair.getKey().toString().equals("preferenceId")) {
                            predicates.add(criteriaBuilder.equal(root.get(pair.getKey().toString()), pair.getValue().toString()));
                        }
                    }
                }
                select.where(predicates.toArray(new Predicate[]{}));
            }
            if (criterias.get(Keys.ORDER_BY) != null) {
                List<Order> orderList = new ArrayList<>();

                for (Object o : criterias.get(Keys.ORDER_BY).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if(pair.getKey().toString().equals("preferenceId") || pair.getKey().toString().equals("name")) {
                        if (pair.getValue().toString().toLowerCase().equals("asc")) {
                            orderList.add(criteriaBuilder.asc(root.get(pair.getKey().toString())));
                        } else if (pair.getValue().toString().toLowerCase().equals("desc")) {
                            orderList.add(criteriaBuilder.desc(root.get(pair.getKey().toString())));
                        }
                    } else {
                        if (pair.getValue().toString().toLowerCase().equals("asc")) {
                            orderList.add(criteriaBuilder.asc(root.get(pair.getKey().toString())));
                        } else if (pair.getValue().toString().toLowerCase().equals("desc")) {
                            orderList.add(criteriaBuilder.desc(root.get(pair.getKey().toString())));
                        }
                    }
                }
                select.orderBy(orderList);
            }
            if (criterias.get(Keys.PAGINATION) != null) {
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                TypedQuery<PreferencesSearch> typedQuery = session.createQuery(select);
                long total = typedQuery.getResultList().size();
                typedQuery.setFirstResult(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
                typedQuery.setMaxResults(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                page = new PageImpl<PreferencesSearch>(typedQuery.getResultList(), pageable, total);

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