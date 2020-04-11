package com.blesk.accountservice.Service.Logins;

import com.blesk.accountservice.Model.Logins;

public interface LoginsService {

    Logins createLogin(Logins logins);

    Boolean deleteLogin(Long loginId);

    Boolean updateLogin(Logins logins);

    Logins getLogin(Long loginId);
}
