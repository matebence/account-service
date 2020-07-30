package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.Model.Preferences;

import java.util.HashMap;
import java.util.Map;

public interface PreferencesDAO extends DAO<Preferences> {

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}