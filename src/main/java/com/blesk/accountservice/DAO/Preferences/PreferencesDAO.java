package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PreferencesDAO extends DAO<Preferences> {

    Boolean softDelete(Preferences deleted);

    Preferences get(Long id, boolean isDeleted);

    List<Preferences> getAll(int pageNumber, int pageSize, boolean isDeleted);

    Preferences getItemByColumn(String column, String value, boolean isDeleted);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted);
}