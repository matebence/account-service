package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.Model.Privileges;

import java.util.List;

public interface PrivilegesService {

    Privileges createPrivilege(Privileges privileges);

    Boolean deletePrivilege(Long privilegeId);

    Boolean updatePrivilege(Privileges privileges);

    Privileges getPrivilege(Long privilegeId);

    List<Privileges> getAllPrivileges(int pageNumber, int pageSize);

    Privileges getPrivilegeByName(String privilegeName);
}