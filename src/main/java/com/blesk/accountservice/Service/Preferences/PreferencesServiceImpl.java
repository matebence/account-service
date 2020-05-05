package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.AccountPreferences;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Preferences createPreference(Preferences preferences) {
        for (AccountPreferences accountPreferences : preferences.getAccountPreferences()) {
            preferences.getAccountPreferences().remove(accountPreferences);
            preferences.addAccount(accountPreferences);
        }

        return this.preferencesDAO.save(preferences);
    }

    @Override
    @Transactional
    public Boolean deletePreference(Long preferenceId, boolean su) {
        if (su){
            Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
            if (preferences == null)
                throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.NOT_FOUND);
            if (!this.preferencesDAO.delete("preferences", "preference_id", preferenceId))
                throw new AccountServiceException(Messages.DELETE_PREFERENCE, HttpStatus.NOT_FOUND);
            return true;
        } else {
            Preferences preferences = this.preferencesDAO.get(preferenceId, false);
            if (preferences == null)
                throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.NOT_FOUND);
            return this.preferencesDAO.softDelete(preferences);
        }
    }

    @Override
    @Transactional
    public Boolean updatePreference(Preferences preferences) {
        return this.preferencesDAO.update(preferences);
    }

    @Override
    @Transactional
    public Preferences getPreference(Long preferenceId, boolean su) {
        if (su) {
            return this.preferencesDAO.get(Preferences.class, preferenceId);
        } else {
            return this.preferencesDAO.get(preferenceId, false);
        }
    }

    @Override
    @Transactional
    public Preferences findPreferenceByName(String name, boolean isDeleted) {
        Preferences preferences = this.preferencesDAO.getItemByColumn("name", name, isDeleted);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE, HttpStatus.NOT_FOUND);
        return preferences;
    }

    @Override
    @Transactional
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.preferencesDAO.getAll(Preferences.class, pageNumber, pageSize);
        } else {
            return this.preferencesDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPreference(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.preferencesDAO.searchBy(Preferences.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.preferencesDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}