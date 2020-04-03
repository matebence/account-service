package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Exceptions.AuthorizationServerException;
import com.blesk.accountservice.Model.Preferences.Preferences;
import com.blesk.accountservice.Values.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferencesServiceImpl implements PreferencesService {

    private PreferencesDAOImpl preferencesDAO;

    @Autowired
    public PreferencesServiceImpl(PreferencesDAOImpl preferencesDAO){
        this.preferencesDAO = preferencesDAO;
    }

    @Override
    public Preferences createPreference(Preferences preferences) {
        if (this.preferencesDAO.save(preferences).getPreferenceId() == null)
            throw new AuthorizationServerException(Messages.CREATE_PREFERENCE);
        return preferences;
    }

    @Override
    public boolean deletePreference(Long preferenceId) {
        Preferences preferences = this.preferencesDAO.get(Preferences.class, preferenceId);
        if(preferences == null)
            throw new AuthorizationServerException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.delete(preferences))
            throw new AuthorizationServerException(Messages.DELETE_PREFEREBCE);
        return true;
    }

    @Override
    public boolean updatePreference(Preferences preferences) {
        if (!this.preferencesDAO.update(preferences))
            throw new AuthorizationServerException(Messages.UPDATE_PREFEREBCE);
        return true;
    }

    @Override
    public Preferences getPreference(Long preferenceId) {
        Preferences preference = this.preferencesDAO.get(Preferences.class, preferenceId);
        if(preference == null)
            throw new AuthorizationServerException(Messages.GET_PREFERENCE);
        return preference;
    }

    @Override
    public List<Preferences> getAllPreferences(int pageNumber, int pageSize) {
        List<Preferences> preferences = this.preferencesDAO.getAll(Preferences.class, pageNumber, pageSize);
        if(preferences == null)
            throw new AuthorizationServerException(Messages.GET_ALL_PREFERENCES);
        return preferences;
    }

    @Override
    public Preferences getPreferenceByName(String preferenceName) {
        Preferences preference = this.preferencesDAO.getPreferenceByName(preferenceName);
        if(preference == null)
            throw new AuthorizationServerException(Messages.GET_PREFERENCE_BY_NAME);
        return preference;
    }
}