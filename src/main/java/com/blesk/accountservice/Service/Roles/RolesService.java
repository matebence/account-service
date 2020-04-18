package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Model.RolePrivilegeItems.RolePrivileges;
import com.blesk.accountservice.Model.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RolesService {

    Roles createRole(Roles roles, JwtMapper jwtMapper);

    Boolean softDeleteRole(Long roleId, JwtMapper jwtMapper);

    Boolean deleteRole(Long roleId);

    Boolean updateRole(Roles roles, JwtMapper jwtMapper);

    Roles getRole(Long roleId, boolean isDeleted);

    Roles findRoleByName(String roleName, boolean isDeleted);

    Set<RolePrivileges> findPrivilegesByRoleName(String name, boolean isDeleted);

    List<Roles> getAllRoles(int pageNumber, int pageSize, boolean isDeleted);

    Map<String, Object> searchForRole(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted);
}