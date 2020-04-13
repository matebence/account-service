package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import com.blesk.accountservice.Validator.Service.FieldValueExists;

import java.util.List;

public interface PreferencesService extends FieldValueExists {

    AccountPreferenceItems createPreference(AccountPreferenceItems accountPreferenceItems);

    Boolean deletePreference(Long preferenceId);

    Boolean updatePreference(AccountPreferenceItems accountPreferenceItems);

    AccountPreferenceItems getPreference(Long preferenceId);

    List<AccountPreferenceItems> getAllPreferences(int pageNumber, int pageSize);

    AccountPreferenceItems getPreferenceByName(String preferenceName);
}
