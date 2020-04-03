package com.blesk.accountservice.DAO.Privileges;

import com.blesk.accountservice.Model.Privileges;

public interface PrivilegesDAO {

    Privileges getPrivilegeByName(String name);
}
