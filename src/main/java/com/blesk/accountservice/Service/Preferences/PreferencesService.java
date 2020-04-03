package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.Model.Preferences.Preferences;

import java.util.List;

public interface PreferencesService {

    Preferences createPreference(Preferences preferences);

    boolean deletePreference(Long preferenceId);

    boolean updatePreference(Preferences preferences);

    Preferences getPreference(Long preferenceId);

    List<Preferences> getAllPreferences(int pageNumber, int pageSize);

    Preferences getPreferenceByName(String preferenceName);
}
