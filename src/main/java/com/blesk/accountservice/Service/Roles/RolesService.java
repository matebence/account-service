package com.blesk.accountservice.Service.Roles;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;
import com.blesk.accountservice.Validator.Service.FieldValueExists;

import java.util.List;
import java.util.Set;

public interface RolesService extends FieldValueExists {

    Roles createRole(Roles roles);

    Boolean deleteRole(Long roleId);

    Boolean updateRole(Roles roles);

    Roles getRole(Long roleId);

    List<Roles> getAllRoles(int pageNumber, int pageSize);

    Roles getRoleByName(String roleName);

    Set<Privileges> getRolePrivileges(String roleName);
}