package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountsDAO extends DAO<Accounts> {

    Boolean softDelete(Accounts accounts);

    Accounts get(Long id, boolean isDeleted);

    List<Accounts> getAll(int pageNumber, int pageSize, boolean isDeleted);

    Accounts getItemByColumn(String column, String value, boolean isDeleted);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted);
}