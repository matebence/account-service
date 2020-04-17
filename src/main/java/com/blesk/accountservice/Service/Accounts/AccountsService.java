package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService {

    Accounts createAccount(Accounts accounts);

    Boolean deleteAccount(Long accountId);

    Boolean updateAccount(Accounts accounts);

    Accounts getAccount(Long accountId);

    Accounts findAccountByEmail(String email);

    Accounts findAccountByUsername(String userName);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria);
}