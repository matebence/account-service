package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.DAO.Activations.ActivationsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivationServiceImpl implements ActivationService {

    private ActivationsDAOImpl activationsDAO;

    @Autowired
    public ActivationServiceImpl(ActivationsDAOImpl activationsDAO) {
        this.activationsDAO = activationsDAO;
    }

    @Override
    @Transactional
    public Activations createActivationToken(Activations activations) {
        Activations activation = this.activationsDAO.save(activations);
        if (activation == null)
            throw new AccountServiceException(Messages.CREATE_ACTIVATION_TOKEN);
        return activation;
    }

    @Override
    @Transactional
    public Boolean deleteActivationToken(Long activationTokenId) {
        Activations activations = this.activationsDAO.get(Activations.class, activationTokenId);
        if (activations == null)
            throw new AccountServiceException(Messages.DELETE_GET_ACTIVATION_TOKEN);
        if (!this.activationsDAO.delete(activations))
            throw new AccountServiceException(Messages.DELETE_ACTIVATION_TOKEN);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateActivationToken(Activations activations) {
        if (!this.activationsDAO.update(activations))
            throw new AccountServiceException(Messages.UPDATE_ACTIVATION_TOKEN);
        return true;
    }

    @Override
    @Transactional
    public Activations getActivationToken(Long activationTokenId) {
        Activations activations = this.activationsDAO.get(Activations.class, activationTokenId);
        if (activations == null)
            throw new AccountServiceException(Messages.GET_ACTIVATION_TOKEN);
        return activations;
    }

    @Override
    @Transactional
    public Activations findActivationToken(String token) {
        Activations activations = this.activationsDAO.getItemByColumn(Activations.class, "token", token);
        if (activations == null)
            throw new AccountServiceException(Messages.GET_ACTIVATION_TOKEN);

        return activations;
    }

    @Override
    @Transactional
    public List<Activations> getAllActivationTokens(int pageNumber, int pageSize) {
        List<Activations> activations = this.activationsDAO.getAll(Activations.class, pageNumber, pageSize);
        if (activations.isEmpty())
            throw new AccountServiceException(Messages.GET_ALL_ACTIVATION_TOKEN);
        return activations;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForActivationToken(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> activations = this.activationsDAO.searchBy(Activations.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (activations == null || activations.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return activations;
    }

    @Override
    @Transactional
    public Boolean validateActivationToken(long accountId, String token) {
        Activations activations = this.activationsDAO.getItemByColumn(Activations.class, "token", token);
        if (activations == null)
            throw new AccountServiceException(Messages.VALIDATE_ACTIVATION_TOKEN);

        return activations.getAccounts().getAccountId() == accountId;
    }
}