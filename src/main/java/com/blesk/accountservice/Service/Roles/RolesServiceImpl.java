package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.RolePrivileges;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.*;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesDAOImpl roleDAO;

    @Autowired
    public RolesServiceImpl(RolesDAOImpl roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Roles createRole(Roles roles) {
        for (RolePrivileges privileges : roles.getRolePrivileges()) {
            roles.getRolePrivileges().remove(privileges);
            roles.addPrivilege(privileges);
        }

        return this.roleDAO.save(roles);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(Roles.class, roleId);
        if (roles == null) throw new AccountServiceException(Messages.GET_ROLE, HttpStatus.NOT_FOUND);
        if (!this.roleDAO.delete("roles", "role_id", roleId)) throw new AccountServiceException(Messages.DELETE_ROLE, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateRole(Roles roles) {
        return this.roleDAO.update(roles);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Roles getRole(Long roleId) {
        return this.roleDAO.get(Roles.class, roleId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Roles findRoleByName(String name) {
        Roles role = this.roleDAO.getItemByColumn(Roles.class, "name", name);
        if (role == null) throw new AccountServiceException(Messages.GET_ROLE, HttpStatus.NOT_FOUND);
        return role;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Set<RolePrivileges> findPrivilegesByRoleName(String name) {
        Set<RolePrivileges> rolePrivileges = this.roleDAO.getItemByColumn(Roles.class, "name", name).getRolePrivileges();
        if (rolePrivileges.isEmpty()) throw new AccountServiceException(String.format(Messages.GET_ROLE_PRIVILEGES, name), HttpStatus.NOT_FOUND);
        return rolePrivileges;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Roles> getAllRoles(int pageNumber, int pageSize) {
        return this.roleDAO.getAll(Roles.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criteria) {
        return this.roleDAO.searchBy(Roles.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
    }
}