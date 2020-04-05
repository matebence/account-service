package com.blesk.accountservice.DAO.Privileges;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Privileges;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class PrivilegesDAOImpl extends DAOImpl<Privileges> implements PrivilegesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Privileges getPrivilegeByName(String name) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Privileges> criteriaQuery = criteriaBuilder.createQuery(Privileges.class);
        Root<Privileges> root = criteriaQuery.from(Privileges.class);
        try {
            return this.entityManager.createQuery(criteriaQuery
                    .where(criteriaBuilder.equal(root.get("name"), name))).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
