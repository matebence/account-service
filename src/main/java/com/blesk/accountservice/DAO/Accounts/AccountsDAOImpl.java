package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.DTO.AccountsJoin;
import com.blesk.accountservice.Model.*;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountsDAOImpl extends DAOImpl<Accounts> implements AccountsDAO {

    @Override
    public Boolean update(Accounts accounts) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(accounts);
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
    public List<AccountsJoin> getJoinValuesByColumn(List<Long> ids, List<String> listedRoles, String columName) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AccountsJoin> criteriaQuery = criteriaBuilder.createQuery(AccountsJoin.class);
        Root<Accounts> root = criteriaQuery.from(Accounts.class);
        Join<Accounts, AccountRoles> accountRoles = root.join("accountRoles");
        Join<AccountRoles, Roles> roles = accountRoles.join("roles");
        CompoundSelection<AccountsJoin> selection = criteriaBuilder.construct(AccountsJoin.class, root.get("accountId"), root.get("userName"), root.get("email"), roles.get("name"));

        Predicate rolePredicate = null, idPredicate = null;
        CriteriaQuery<AccountsJoin> select = criteriaQuery.select(selection);

        if (ids != null) {
            Expression<String> parentExpression = root.get(columName);
            idPredicate = parentExpression.in(ids);
        }
        if (listedRoles != null) {
            Expression<String> parentExpression = roles.get("name");
            rolePredicate = parentExpression.in(listedRoles);
        }
        select.where(criteriaBuilder.or(idPredicate), criteriaBuilder.and(rolePredicate), criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false)));
        try {
            return session.createQuery(select).getResultList();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}