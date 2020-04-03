package com.blesk.authorizationserver.Service.Roles;

import com.blesk.authorizationserver.Model.Privileges;
import com.blesk.authorizationserver.Model.Roles;

import java.util.List;
import java.util.Set;

public interface RolesService {

    Roles createRole(Roles roles);

    boolean deleteRole(Long roleId);

    boolean updateRole(Roles roles);

    Roles getRole(Long roleId);

    List<Roles> getAllRoles(int pageNumber, int pageSize);

    Roles getRoleByName(String roleName);

    Set<Privileges> getRolePrivileges(String roleName);
}