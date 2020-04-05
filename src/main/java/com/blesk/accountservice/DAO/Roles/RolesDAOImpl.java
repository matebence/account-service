package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RolesDAOImpl extends DAOImpl<Roles> implements RolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Roles> getListOfRoles(Set<Roles> roles) {
        Session session = this.entityManager.unwrap(Session.class);

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);

        Root<Roles> root = criteriaQuery.from(Roles.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        CriteriaQuery select = criteriaQuery.select(root);

        for (Roles role : roles) {
            predicates.add(criteriaBuilder.equal(root.get("name"), role.getName().toUpperCase()));
        }

        select.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

        try {
            return new HashSet<Roles>(this.entityManager.createQuery(select).getResultList());
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Roles getRoleByName(String name) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);

        try {
            return this.entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Set<Privileges> getPrivilegesAssignedToRole(String name) {
        return getRoleByName(name).getPrivileges();
    }
}