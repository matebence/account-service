package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RolesDAO extends DAO<Roles> {

    Boolean softDelete(Roles roles);

    Roles get(Long id);

    List<Roles> getAll(int pageNumber, int pageSize);

    Roles getItemByColumn(String column, String value);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}