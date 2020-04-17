package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
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
        Preferences preference = this.preferencesDAO.save(preferences);
        if (preference == null)
            throw new AccountServiceException(Messages.CREATE_PREFERENCE);
        return preference;
    }

    @Override
    @Transactional
    public Boolean deletePreference(Long preferenceId) {
        Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
        if (preferences == null)
            throw new AccountServiceException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.delete(preferences))
            throw new AccountServiceException(Messages.DELETE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePreference(Preferences preferences) {
        if (!this.preferencesDAO.update(preferences))
            throw new AccountServiceException(Messages.UPDATE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Preferences getPreference(Long preferenceId) {
        Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return preferences;
    }

    @Override
    @Transactional
    public Preferences findPreferenceByName(String name) {
        Preferences preferences = this.preferencesDAO.getItemByColumn(Preferences.class, "name", name);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return preferences;
    }

    @Override
    @Transactional
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize) {
        List<Preferences> preferences = this.preferencesDAO.getAll(Preferences.class, pageNumber, pageSize);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_ALL_PREFERENCES);
        return preferences;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPreferences(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> prefrences = this.preferencesDAO.searchBy(Preferences.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (prefrences == null || prefrences.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return prefrences;
    }
}