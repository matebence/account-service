package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Model.RolePrivileges;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Value.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
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
        return this.roleDAO.delete("roles", "role_id", roleId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateRole(Roles role, Roles roles) {
        role.setName(getNotNull(roles.getName(), role.getName()));
        if (roles.getRolePrivileges() != null){
            for (RolePrivileges rolePrivilege : role.getRolePrivileges()) {
                for (RolePrivileges rolePrivileges : roles.getRolePrivileges()) {
                    if (rolePrivileges.getDeleted() == null) {
                        role.addPrivilege(rolePrivileges);
                    } else if (rolePrivileges.getDeleted()) {
                        role.removePrivilege(rolePrivilege);
                    } else {
                        rolePrivilege.setPrivileges(rolePrivileges.getPrivileges());
                    }
                }
            }
        }

        return this.roleDAO.update(role);
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
        return this.roleDAO.getItemByColumn(Roles.class, "name", name);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Set<RolePrivileges> findPrivilegesByRoleName(String name) {
        return this.roleDAO.getItemByColumn(Roles.class, "name", name).getRolePrivileges();
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

    private static <T> T getNotNull(T a, T b) {
        return b != null && a != null && !a.equals(b) ? a : b;
    }
}