package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.DTO.AccountsJoin;
import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService {

    Accounts createAccount(Accounts accounts, String[] allowedRoles);

    Boolean deleteAccount(Accounts accounts);

    Boolean updateAccount(Accounts account, Accounts accounts, String[] allowedRoles);

    Accounts getAccount(Long accountId);

    Accounts findAccountByEmail(String email);

    Accounts findAccountByUsername(String userName);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize);

    List<AccountsJoin> getAccountsForJoin(List<Long> ids, List<String> listedRoles, String columName);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criterias);
}