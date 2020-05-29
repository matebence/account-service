package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsDAO extends DAO<Accounts> {

    Boolean softDelete(Accounts accounts);

    Accounts get(Long id);

    List<Accounts> getAll(int pageNumber, int pageSize);

    Accounts getItemByColumn(String column, String value);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}