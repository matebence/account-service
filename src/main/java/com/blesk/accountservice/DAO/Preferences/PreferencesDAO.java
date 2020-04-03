package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.Model.Preferences.Preferences;

public interface PreferencesDAO {

    Preferences getPreferenceByName(String name);
}
