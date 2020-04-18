package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.DTO.JwtMapper;
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
    public Preferences createPreference(Preferences preferences, JwtMapper jwtMapper) {
        Preferences preference = this.preferencesDAO.save(preferences);
        if (preference == null)
            throw new AccountServiceException(Messages.CREATE_PREFERENCE);
        return preference;
    }

    @Override
    @Transactional
    public Boolean softDeletePreference(Long preferenceId, JwtMapper jwtMapper) {
        Preferences preferences = this.preferencesDAO.get(preferenceId, false);
        if (preferences == null)
            throw new AccountServiceException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.softDelete(preferences))
            throw new AccountServiceException(Messages.DELETE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Boolean deletePreference(Long preferenceId) {
        Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
        if (preferences == null)
            throw new AccountServiceException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.delete("preferences", "preference_id", preferenceId))
            throw new AccountServiceException(Messages.DELETE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePreference(Preferences preferences, JwtMapper jwtMapper) {
        if (!this.preferencesDAO.update(preferences))
            throw new AccountServiceException(Messages.UPDATE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Preferences getPreference(Long preferenceId, boolean isDeleted) {
        Preferences preferences = this.preferencesDAO.get(preferenceId, isDeleted);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return preferences;
    }

    @Override
    @Transactional
    public Preferences findPreferenceByName(String name, boolean isDeleted) {
        Preferences preferences = this.preferencesDAO.getItemByColumn("name", name, isDeleted);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return preferences;
    }

    @Override
    @Transactional
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize, boolean isDeleted) {
        List<Preferences> preferences = this.preferencesDAO.getAll(pageNumber, pageSize, isDeleted);
        if (preferences == null)
            throw new AccountServiceException(Messages.GET_ALL_PREFERENCES);
        return preferences;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPreferences(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> prefrences = this.preferencesDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), isDeleted);

        if (prefrences == null || prefrences.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return prefrences;
    }
}