package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.Map;

public interface AccountsDAO extends DAO<Accounts> {

    Accounts getAccountInformations(String userName);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber);
}