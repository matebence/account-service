package com.blesk.authorizationserver.DAO.Preferences;

import com.blesk.authorizationserver.Model.Preferences.Preferences;

public interface PreferencesDAO {

    Preferences getPreferenceByName(String name);
}
