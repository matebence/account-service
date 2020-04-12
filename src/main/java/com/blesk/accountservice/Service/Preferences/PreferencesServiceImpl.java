package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Preferences.Preferences;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        if (this.preferencesDAO.save(preferences).getPreferenceId() == null)
            throw new AccountServiceException(Messages.CREATE_PREFERENCE);
        return preferences;
    }

    @Override
    @Transactional
    public Boolean deletePreference(Long preferenceId) {
        Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
        if (preferences == null)
            throw new AccountServiceException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.delete(preferences))
            throw new AccountServiceException(Messages.DELETE_PREFEREBCE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePreference(Preferences preferences) {
        if (!this.preferencesDAO.update(preferences))
            throw new AccountServiceException(Messages.UPDATE_PREFEREBCE);
        return true;
    }

    @Override
    @Transactional
    public Preferences getPreference(Long preferenceId) {
        Preferences preference = this.preferencesDAO.get(Preferences.class, preferenceId);
        if (preference == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return preference;
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
    public Preferences getPreferenceByName(String preferenceName) {
        Preferences preference = this.preferencesDAO.getPreferenceByName(preferenceName);
        if (preference == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE_BY_NAME);
        return preference;
    }
}