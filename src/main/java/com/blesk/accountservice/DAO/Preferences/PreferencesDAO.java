package com.blesk.accountservice.DAO.Preferences;

import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;

public interface PreferencesDAO {

    AccountPreferenceItems getPreferenceByName(String name);
}
