package com.blesk.accountservice.DAO.Privileges;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Privileges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PrivilegesDAO extends DAO<Privileges> {

    Boolean softDelete(Privileges privileges);

    Privileges get(Long id);

    List<Privileges> getAll(int pageNumber, int pageSize);

    Privileges getItemByColumn(String column, String value);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}