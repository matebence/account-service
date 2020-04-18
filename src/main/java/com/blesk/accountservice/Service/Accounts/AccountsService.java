package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService {

    Accounts createAccount(Accounts accounts, JwtMapper jwtMapper);

    Boolean softDeleteAccount(Long accountId, JwtMapper jwtMapper);

    Boolean deleteAccount(Long accountId);

    Boolean updateAccount(Accounts accounts, JwtMapper jwtMapper);

    Accounts getAccount(Long accountId, boolean isDeleted);

    Accounts findAccountByEmail(String email, boolean isDeleted);

    Accounts findAccountByUsername(String userName, boolean isDeleted);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean isDeleted);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted);
}