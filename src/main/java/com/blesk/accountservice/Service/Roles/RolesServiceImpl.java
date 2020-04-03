package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Exceptions.AccountServiceException;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Values.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesDAOImpl roleDAO;

    @Autowired
    public RolesServiceImpl(RolesDAOImpl roleDAO){
        this.roleDAO = roleDAO;
    }

    @Override
    public Roles createRole(Roles roles) {
        if (this.roleDAO.save(roles).getRoleId() == null)
            throw new AccountServiceException(Messages.CREATE_ROLE);
        return roles;
    }

    @Override
    public boolean deleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(Roles.class, roleId);
        if(roles == null)
            throw new AccountServiceException(Messages.DELETE_GET_ROLE);
        if (!this.roleDAO.delete(roles))
            throw new AccountServiceException(Messages.DELETE_ROLE);
        return true;
    }

    @Override
    public boolean updateRole(Roles roles) {
        if (!this.roleDAO.update(roles))
            throw new AccountServiceException(Messages.UPDATE_ROLE);
        return true;
    }

    @Override
    public Roles getRole(Long roleId) {
        Roles role = this.roleDAO.get(Roles.class, roleId);
        if(role == null)
            throw new AccountServiceException(Messages.GET_ROLE);
        return role;
    }

    @Override
    public List<Roles> getAllRoles(int pageNumber, int pageSize) {
        List<Roles> roles = this.roleDAO.getAll(Roles.class, pageNumber, pageSize);
        if(roles == null)
            throw new AccountServiceException(Messages.GET_ALL_ROLES);
        return roles;
    }

    @Override
    public Roles getRoleByName(String roleName) {
        Roles role = this.roleDAO.getRoleByName(roleName);
        if(role == null)
            throw new AccountServiceException(Messages.GET_ROLE_BY_NAME);
        return role;
    }

    @Override
    public Set<Privileges> getRolePrivileges(String roleName) {
        Set<Privileges> privileges = this.roleDAO.getPrivilegesAssignedToRole(roleName);
        if (privileges.isEmpty())
            throw new AccountServiceException(String.format(Messages.GET_ROLE_PRIVILEGES, roleName));
        return privileges;
    }
}
