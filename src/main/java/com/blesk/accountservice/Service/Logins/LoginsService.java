package com.blesk.accountservice.Service.Logins;

import com.blesk.accountservice.Model.Logins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LoginsService {

    Logins createLogin(Logins logins);

    Boolean deleteLogin(Long loginId);

    Boolean updateLogin(Logins logins);

    Logins getLogin(Long loginId);

    Logins findLoginByIpAddress(String ipAddress);

    List<Logins> getAllLogins(int pageNumber, int pageSize);

    Map<String, Object> searchForLogin(HashMap<String, HashMap<String, String>> criterias);
}