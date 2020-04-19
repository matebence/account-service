package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.Model.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PreferencesService {

    Preferences createPreference(Preferences preferences);

    Boolean softDeletePreference(Long preferenceId);

    Boolean deletePreference(Long preferenceId);

    Boolean updatePreference(Preferences preferences);

    Preferences getPreference(Long preferenceId, boolean su);

    Preferences findPreferenceByName(String name, boolean isDeleted);

    List<Preferences> getAllPreferences(int pageNumber, int pageSize, boolean su);

    Map<String, Object> searchForPreferences(HashMap<String, HashMap<String, String>> criteria, boolean su);
}