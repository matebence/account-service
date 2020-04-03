package com.blesk.authorizationserver.DAO.Roles;

import com.blesk.authorizationserver.Model.Privileges;
import com.blesk.authorizationserver.Model.Roles;

import java.util.ArrayList;
import java.util.Set;

public interface RolesDAO {
    Set<Roles> getListOfRoles(ArrayList<String> names);

    Roles getRoleByName(String name);

    Set<Privileges> getPrivilegesAssignedToRole(String name);
}
