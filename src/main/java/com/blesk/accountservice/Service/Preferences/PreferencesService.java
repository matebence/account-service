package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.Model.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PreferencesService {

    Preferences createPreference(Preferences preferences);

    Boolean deletePreference(Preferences preferences);

    Boolean updatePreference(Preferences preference, Preferences preferences);

    Preferences getPreference(Long preferenceId);

    Preferences findPreferenceByName(String name);

    List<Preferences> getAllPreferences(int pageNumber, int pageSize);

    Map<String, Object> searchForPreference(HashMap<String, HashMap<String, String>> criterias);
}