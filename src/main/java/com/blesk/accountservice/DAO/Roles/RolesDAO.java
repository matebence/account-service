package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;

import java.util.Set;

public interface RolesDAO {

    Set<Roles> getListOfRoles(Set<Roles> names);

    Roles getRoleByName(String name);

    Set<Privileges> getPrivilegesAssignedToRole(String name);
}
