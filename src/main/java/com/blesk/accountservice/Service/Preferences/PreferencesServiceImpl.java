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
        for (AccountPreferences accountPreferences : preferences.getAccountPreferences()) {
            preferences.getAccountPreferences().remove(accountPreferences);
            preferences.addAccount(accountPreferences);
        }

        return this.preferencesDAO.save(preferences);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePreference(Preferences preferences, boolean su) {
        if (su){
            return this.preferencesDAO.delete("preferences", "preference_id", preferences.getPreferenceId());
        } else {
            return this.preferencesDAO.softDelete(preferences);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePreference(Preferences preference, Preferences preferences) {
        preference.setName(preferences.getName());
        for (AccountPreferences accountPreference : preference.getAccountPreferences()) {
            for (AccountPreferences accountPreferences : preferences.getAccountPreferences()) {
                if (accountPreferences.getDeleted() == null) {
                    preference.addAccount(accountPreferences);
                } else if (accountPreferences.getDeleted()) {
                    preference.removeAccount(accountPreference);
                } else {
                    accountPreference.setAccounts(accountPreferences.getAccounts());
                }
            }
        }
        return this.preferencesDAO.update(preference);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Preferences getPreference(Long preferenceId, boolean su) {
        if (su) {
            return this.preferencesDAO.get(Preferences.class, preferenceId);
        } else {
            return this.preferencesDAO.get(preferenceId);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Preferences findPreferenceByName(String name, boolean su) {
        if (su) {
            return this.preferencesDAO.getItemByColumn(Preferences.class,"name", name);
        } else {
            return this.preferencesDAO.getItemByColumn("name", name);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.preferencesDAO.getAll(Preferences.class, pageNumber, pageSize);
        } else {
            return this.preferencesDAO.getAll(pageNumber, pageSize);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPreference(HashMap<String, HashMap<String, String>> criterias, boolean su) {
        if (su) {
            return this.preferencesDAO.searchBy(Preferences.class, criterias);
        } else {
            return this.preferencesDAO.searchBy(criterias);
        }
    }
}