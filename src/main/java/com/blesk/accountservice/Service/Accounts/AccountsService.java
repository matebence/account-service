package com.blesk.accountservice.Service.Accounts;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Validator.Service.FieldValueExists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsService extends FieldValueExists {

    Activations createAccount(Accounts accounts, String[] allowedRoles);

    Boolean deleteAccount(Long accountId);

    Boolean updateAccount(Accounts accounts);

    Accounts getAccount(Long accountId);

    List<Accounts> getAllAccounts(int pageNumber, int pageSize);

    Accounts getAccountInformations(String userName);

    Accounts findAccountByEmail(String email);

    Map<String, Object> searchForAccount(HashMap<String, HashMap<String, String>> criteria);
}