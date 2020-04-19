package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.RolePrivilegeItems.RolePrivileges;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesDAOImpl roleDAO;

    @Autowired
    public RolesServiceImpl(RolesDAOImpl roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional
    public Roles createRole(Roles roles) {
        return this.roleDAO.save(roles);
    }

    @Override
    @Transactional
    public Boolean softDeleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(roleId, false);
        if (roles == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        return this.roleDAO.softDelete(roles);
    }

    @Override
    @Transactional
    public Boolean deleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(Roles.class, roleId);
        if (roles == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        if (!this.roleDAO.delete("roles", "role_id", roleId))
            throw new AccountServiceException(Messages.DELETE_ROLE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateRole(Roles roles) {
        return this.roleDAO.update(roles);
    }

    @Override
    @Transactional
    public Roles getRole(Long roleId) {
        return this.roleDAO.get(Roles.class, roleId);
    }

    @Override
    @Transactional
    public Roles findRoleByName(String name) {
        Roles role = this.roleDAO.getItemByColumn(Roles.class, "name", name);
        if (role == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        return role;
    }

    @Override
    @Transactional
    public Set<RolePrivileges> findPrivilegesByRoleName(String name) {
        Set<RolePrivileges> rolePrivileges = this.roleDAO.getItemByColumn(Roles.class, "name", name).getRolePrivileges();
        if (rolePrivileges.isEmpty())
            throw new AccountServiceException(String.format(Messages.GET_ROLE_PRIVILEGES, name));
        return rolePrivileges;
    }

    @Override
    @Transactional
    public List<Roles> getAllRoles(int pageNumber, int pageSize) {
        return this.roleDAO.getAll(Roles.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criteria) {
        return this.roleDAO.searchBy(Roles.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
    }
}