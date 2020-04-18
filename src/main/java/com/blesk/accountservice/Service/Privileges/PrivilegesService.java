package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Model.Privileges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PrivilegesService {

    Privileges createPrivilege(Privileges privileges, JwtMapper jwtMapper);

    Boolean softDeletePrivilege(Long privilegeId, JwtMapper jwtMapper);

    Boolean deletePrivilege(Long privilegeId);

    Boolean updatePrivilege(Privileges privileges, JwtMapper jwtMapper);

    Privileges getPrivilege(Long privilegeId, boolean isDeleted);

    Privileges findPrivilegeByName(String name, boolean isDeleted);

    List<Privileges> getAllPrivileges(int pageNumber, int pageSize, boolean isDeleted);

    Map<String, Object> searchForPrivileges(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted);
}