package com.blesk.accountservice.DAO.Roles;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RolesDAO extends DAO<Roles> {

    Boolean softDelete(Roles roles);

    Roles get(Long id, boolean isDeleted);

    List<Roles> getAll(int pageNumber, int pageSize, boolean isDeleted);

    Roles getItemByColumn(String column, String value, boolean isDeleted);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted);
}