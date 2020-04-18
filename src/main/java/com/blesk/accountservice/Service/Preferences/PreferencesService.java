package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DTO.JwtMapper;
import com.blesk.accountservice.Model.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PreferencesService {

    Preferences createPreference(Preferences preferences, JwtMapper jwtMapper);

    Boolean softDeletePreference(Long preferenceId, JwtMapper jwtMapper);

    Boolean deletePreference(Long preferenceId);

    Boolean updatePreference(Preferences preferences, JwtMapper jwtMapper);

    Preferences getPreference(Long preferenceId, boolean isDeleted);

    Preferences findPreferenceByName(String name, boolean isDeleted);

    List<Preferences> getAllPreferences(int pageNumber, int pageSize, boolean isDeleted);

    Map<String, Object> searchForPreferences(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted);
}