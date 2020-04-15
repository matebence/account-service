package com.blesk.accountservice.Service.Preferences;

import com.blesk.accountservice.DAO.Preferences.PreferencesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import com.blesk.accountservice.Model.Preferences.Preferences;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AccountPreferenceItems createPreference(AccountPreferenceItems accountPreferenceItems) {
        if (this.preferencesDAO.save(accountPreferenceItems).getPreferences() == null)
            throw new AccountServiceException(Messages.CREATE_PREFERENCE);
        return accountPreferenceItems;
    }

    @Override
    @Transactional
    public Boolean deletePreference(Long preferenceId) {
        AccountPreferenceItems accountPreferenceItems = this.preferencesDAO.get(AccountPreferenceItems.class, preferenceId);
        if (accountPreferenceItems == null)
            throw new AccountServiceException(Messages.DELETE_GET_PREFERENCE);
        if (!this.preferencesDAO.delete(accountPreferenceItems))
            throw new AccountServiceException(Messages.DELETE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePreference(AccountPreferenceItems accountPreferenceItems) {
        if (!this.preferencesDAO.update(accountPreferenceItems))
            throw new AccountServiceException(Messages.UPDATE_PREFERENCE);
        return true;
    }

    @Override
    @Transactional
    public AccountPreferenceItems getPreference(Long preferenceId) {
        AccountPreferenceItems accountPreferenceItems = this.preferencesDAO.get(AccountPreferenceItems.class, preferenceId);
        if (accountPreferenceItems == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE);
        return accountPreferenceItems;
    }

    @Override
    @Transactional
    public List<AccountPreferenceItems> getAllPreferences(int pageNumber, int pageSize) {
        List<AccountPreferenceItems> accountPreferenceItems = this.preferencesDAO.getAll(AccountPreferenceItems.class, pageNumber, pageSize);
        if (accountPreferenceItems == null)
            throw new AccountServiceException(Messages.GET_ALL_PREFERENCES);
        return accountPreferenceItems;
    }

    @Override
    @Transactional
    public AccountPreferenceItems getPreferenceByName(String preferenceName) {
        AccountPreferenceItems accountPreferenceItems = this.preferencesDAO.getPreferenceByName(preferenceName);
        if (accountPreferenceItems == null)
            throw new AccountServiceException(Messages.GET_PREFERENCE_BY_NAME);
        return accountPreferenceItems;
    }

    @Override
    @Transactional
    public Boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (value == null || fieldName == null)
            throw new UnsupportedOperationException(String.format(Messages.UNSUPPORTED_COLUMN, fieldName));

        return this.preferencesDAO.unique(AccountPreferenceItems.class, fieldName, value.toString());
    }
}