package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.Model.Privileges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PrivilegesService {

    Privileges createPrivilege(Privileges privileges);

    Boolean deletePrivilege(Privileges privileges);

    Boolean updatePrivilege(Privileges privilege, Privileges privileges);

    Privileges getPrivilege(Long privilegeId);

    Privileges findPrivilegeByName(String name);

    List<Privileges> getAllPrivileges(int pageNumber, int pageSize);

    List<Privileges> getPrivilegesForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForPrivilege(HashMap<String, HashMap<String, String>> criterias);
}