package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.Model.Passwords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PasswordsService {

    Passwords createPasswordToken(Passwords passwords);

    Boolean deletePasswordToken(Long passwordTokenId);

    Boolean updatePasswordToken(Passwords passwords);

    Passwords getPasswordToken(Long passwordTokenId);

    Passwords findPasswordToken(String token);

    List<Passwords> getAllPasswordTokens(int pageNumber, int pageSize);

    Map<String, Object> searchForPasswordToken(HashMap<String, HashMap<String, String>> criterias);

    Boolean validatePasswordToken(long accountId, String token);

    Boolean generateNewPassword(long accountId);
}