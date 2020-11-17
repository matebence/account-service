package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DAO.Roles.RolesDAOImpl;
import com.blesk.accountservice.Model.RolePrivileges;
import com.blesk.accountservice.Model.Roles;
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
        Roles role = null;
        for (RolePrivileges rolePrivileges: roles.getRolePrivileges()) {
            if (role == null) {
                role = this.roleDAO.save(roles);
                rolePrivileges.setRoles(role);
            } else {
                rolePrivileges.setRoles(role);
            }
        }
        return role;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteRole(Roles roles) {
        return this.roleDAO.softDelete(roles);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateRole(Roles role, Roles roles) {
        role.setName(roles.getName());
        role.removeAllPrivileges(new HashSet<>(role.getRolePrivileges()));
        role.addAllPrivileges(roles.getRolePrivileges());

        return this.roleDAO.update(role);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Roles getRole(Long roleId) {
        return this.roleDAO.get(Roles.class, "roleId", roleId);
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
    public List<Roles> getRolesForJoin(List<Long> ids, String columName) {
        return this.roleDAO.getJoinValuesByColumn(Roles.class, ids, columName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criterias) {
        return this.roleDAO.searchBy(Roles.class, criterias);
    }
}