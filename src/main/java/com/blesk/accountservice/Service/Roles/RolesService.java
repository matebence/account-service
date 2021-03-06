package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.Model.RolePrivileges;
import com.blesk.accountservice.Model.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RolesService {

    Roles createRole(Roles roles);

    Boolean deleteRole(Roles roles);

    Boolean updateRole(Roles role, Roles roles);

    Roles getRole(Long roleId);

    Roles findRoleByName(String roleName);

    Set<RolePrivileges> findPrivilegesByRoleName(String name);

    List<Roles> getAllRoles(int pageNumber, int pageSize);

    List<Roles> getRolesForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criterias);
}