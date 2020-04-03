package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;

import java.util.ArrayList;
import java.util.Set;

public interface RolesDAO {
    Set<Roles> getListOfRoles(ArrayList<String> names);

    Roles getRoleByName(String name);

    Set<Privileges> getPrivilegesAssignedToRole(String name);
}
