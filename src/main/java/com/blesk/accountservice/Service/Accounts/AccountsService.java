package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService {

    Accounts createAccount(Accounts accounts, String[] allowedRoles);

    Boolean deleteAccount(Accounts accounts, boolean su);

    Boolean updateAccount(Accounts account, Accounts accounts, String[] allowedRoles);

    Accounts getAccount(Long accountId, boolean su);

    Accounts findAccountByEmail(String email, boolean isDeleted);

    Accounts findAccountByUsername(String userName, boolean isDeleted);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize, boolean su);

    List<Accounts> getAccountsForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria, boolean su);
}