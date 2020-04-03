package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.Model.Accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService {

    Accounts createAccount(Accounts accounts, ArrayList<String> names);

    boolean deleteAccount(Long accountId);

    boolean updateAccount(Accounts accounts);

    Accounts getAccount(Long accountId);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize);

    Accounts getAccountInformations(String userName);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria);
}