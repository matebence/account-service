package com.blesk.authorizationserver.Service.Roles;

import com.blesk.authorizationserver.DAO.Roles.RolesDAOImpl;
import com.blesk.authorizationserver.Exceptions.AuthorizationServerException;
import com.blesk.authorizationserver.Model.Privileges;
import com.blesk.authorizationserver.Model.Roles;
import com.blesk.authorizationserver.Values.Messages;
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
            throw new AuthorizationServerException(Messages.CREATE_ROLE);
        return roles;
    }

    @Override
    public boolean deleteRole(Long roleId) {
        Roles roles = this.roleDAO.get(Roles.class, roleId);
        if(roles == null)
            throw new AuthorizationServerException(Messages.DELETE_GET_ROLE);
        if (!this.roleDAO.delete(roles))
            throw new AuthorizationServerException(Messages.DELETE_ROLE);
        return true;
    }

    @Override
    public boolean updateRole(Roles roles) {
        if (!this.roleDAO.update(roles))
            throw new AuthorizationServerException(Messages.UPDATE_ROLE);
        return true;
    }

    @Override
    public Roles getRole(Long roleId) {
        Roles role = this.roleDAO.get(Roles.class, roleId);
        if(role == null)
            throw new AuthorizationServerException(Messages.GET_ROLE);
        return role;
    }

    @Override
    public List<Roles> getAllRoles(int pageNumber, int pageSize) {
        List<Roles> roles = this.roleDAO.getAll(Roles.class, pageNumber, pageSize);
        if(roles == null)
            throw new AuthorizationServerException(Messages.GET_ALL_ROLES);
        return roles;
    }

    @Override
    public Roles getRoleByName(String roleName) {
        Roles role = this.roleDAO.getRoleByName(roleName);
        if(role == null)
            throw new AuthorizationServerException(Messages.GET_ROLE_BY_NAME);
        return role;
    }

    @Override
    public Set<Privileges> getRolePrivileges(String roleName) {
        Set<Privileges> privileges = this.roleDAO.getPrivilegesAssignedToRole(roleName);
        if (privileges.isEmpty())
            throw new AuthorizationServerException(String.format(Messages.GET_ROLE_PRIVILEGES, roleName));
        return privileges;
    }
}
