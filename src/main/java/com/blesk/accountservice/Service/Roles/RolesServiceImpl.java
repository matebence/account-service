package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.DTO.JwtMapper;
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
    public Roles createRole(Roles roles, JwtMapper jwtMapper) {
        Roles role = this.roleDAO.save(roles);
        if (role == null)
            throw new AccountServiceException(Messages.CREATE_ROLE);
        return role;
    }

    @Override
    @Transactional
    public Boolean softDeleteRole(Long roleId, JwtMapper jwtMapper) {
        Roles roles = this.roleDAO.get(roleId, false);
        if (roles == null)
            throw new AccountServiceException(Messages.DELETE_GET_ROLE);
        if (!this.roleDAO.softDelete(roles))
            throw new AccountServiceException(Messages.DELETE_ROLE);
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(Roles.class, roleId);
        if (roles == null)
            throw new AccountServiceException(Messages.DELETE_GET_ROLE);
        if (!this.roleDAO.delete("roles", "role_id", roleId))
            throw new AccountServiceException(Messages.DELETE_ROLE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateRole(Roles roles, JwtMapper jwtMapper) {
        if (!this.roleDAO.update(roles))
            throw new AccountServiceException(Messages.UPDATE_ROLE);
        return true;
    }

    @Override
    @Transactional
    public Roles getRole(Long roleId, boolean isDeleted) {
        Roles role = this.roleDAO.get(roleId, isDeleted);
        if (role == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        return role;
    }

    @Override
    @Transactional
    public Roles findRoleByName(String name, boolean isDeleted) {
        Roles role = this.roleDAO.getItemByColumn("name", name, isDeleted);
        if (role == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        return role;
    }

    @Override
    @Transactional
    public Set<RolePrivileges> findPrivilegesByRoleName(String name, boolean isDeleted) {
        Set<RolePrivileges> rolePrivileges = this.roleDAO.getItemByColumn("name", name, isDeleted).getRolePrivileges();
        if (rolePrivileges.isEmpty())
            throw new AccountServiceException(String.format(Messages.GET_ROLE_PRIVILEGES, name));
        return rolePrivileges;
    }

    @Override
    @Transactional
    public List<Roles> getAllRoles(int pageNumber, int pageSize, boolean isDeleted) {
        List<Roles> roles = this.roleDAO.getAll(pageNumber, pageSize, isDeleted);
        if (roles == null)
            throw new AccountServiceException(Messages.GET_ALL_ROLES);
        return roles;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> roles = this.roleDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), isDeleted);

        if (roles == null || roles.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return roles;
    }
}