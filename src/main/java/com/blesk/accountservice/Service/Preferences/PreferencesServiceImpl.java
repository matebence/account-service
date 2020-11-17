package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Model.AccountPreferences;
import com.blesk.accountservice.Model.Preferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class PreferencesServiceImpl implements PreferencesService {

    private PreferencesDAOImpl preferencesDAO;

    @Autowired
    public PreferencesServiceImpl(PreferencesDAOImpl preferencesDAO) {
        this.preferencesDAO = preferencesDAO;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Preferences createPreference(Preferences preferences) {
        Preferences preference = null;
        for (AccountPreferences accountPreferences: preferences.getAccountPreferences()) {
            if (preference == null) {
                preference = this.preferencesDAO.save(preferences);
                accountPreferences.setPreferences(preference);
            } else {
                accountPreferences.setPreferences(preference);
            }
        }
        return preference;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePreference(Preferences preferences) {
        return this.preferencesDAO.softDelete(preferences);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePreference(Preferences preference, Preferences preferences) {
        preference.setName(preferences.getName());
        preference.removeAllAccounts(new HashSet<>(preference.getAccountPreferences()));
        preference.addAllAccounts(preferences.getAccountPreferences());

        return this.preferencesDAO.update(preference);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Preferences getPreference(Long preferenceId) {
        return this.preferencesDAO.get(Preferences.class, "preferenceId", preferenceId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Preferences findPreferenceByName(String name) {
        return this.preferencesDAO.getItemByColumn(Preferences.class, "name", name);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize) {
        return this.preferencesDAO.getAll(Preferences.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Preferences> getPreferencesForJoin(List<Long> ids, String columName) {
        return this.preferencesDAO.getJoinValuesByColumn(Preferences.class, ids, columName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPreference(HashMap<String, HashMap<String, String>> criterias) {
        return this.preferencesDAO.searchBy(criterias);
    }
}