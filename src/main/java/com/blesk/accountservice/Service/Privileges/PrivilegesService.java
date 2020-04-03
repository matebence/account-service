package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.Model.Privileges;

import java.util.List;

public interface PrivilegesService {

    Privileges createPrivilege(Privileges privileges);

    boolean deletePrivilege(Long privilegeId);

    boolean updatePrivilege(Privileges privileges);

    Privileges getPrivilege(Long privilegeId);

    List<Privileges> getAllPrivileges(int pageNumber, int pageSize);

    Privileges getPrivilegeByName(String privilegeName);
}