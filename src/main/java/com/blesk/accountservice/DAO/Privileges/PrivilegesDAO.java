package com.blesk.authorizationserver.DAO.Privileges;

import com.blesk.authorizationserver.Model.Privileges;

public interface PrivilegesDAO {

    Privileges getPrivilegeByName(String name);
}
