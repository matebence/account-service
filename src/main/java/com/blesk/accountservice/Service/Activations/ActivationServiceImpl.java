package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.DAO.Activations.ActivationsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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
    @Lock(value = LockModeType.WRITE)
    public Activations createActivationToken(Activations activations) {
        Activations activation = this.activationsDAO.save(activations);
        if (activation == null) throw new AccountServiceException(Messages.CREATE_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return activation;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteActivationToken(Long activationTokenId) {
        if (!this.activationsDAO.delete("activations", "activation_id", activationTokenId)) throw new AccountServiceException(Messages.DELETE_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateActivationToken(Activations activations) {
        if (!this.activationsDAO.update(activations)) throw new AccountServiceException(Messages.UPDATE_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Activations getActivationToken(Long activationTokenId) {
        Activations activations = this.activationsDAO.get(Activations.class, activationTokenId);
        if (activations == null) throw new AccountServiceException(Messages.GET_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return activations;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Activations findActivationToken(String token) {
        Activations activations = this.activationsDAO.getItemByColumn(Activations.class, "token", token);
        if (activations == null) throw new AccountServiceException(Messages.GET_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return activations;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Activations> getAllActivationTokens(int pageNumber, int pageSize) {
        List<Activations> activations = this.activationsDAO.getAll(Activations.class, pageNumber, pageSize);
        if (activations.isEmpty()) throw new AccountServiceException(Messages.GET_ALL_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return activations;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForActivationToken(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null) throw new AccountServiceException(Messages.PAGINATION_ERROR, HttpStatus.NOT_FOUND);
        Map<String, Object> activations = this.activationsDAO.searchBy(Activations.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        if (activations == null || activations.isEmpty()) throw new AccountServiceException(Messages.SEARCH_ERROR, HttpStatus.NOT_FOUND);
        return activations;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Boolean validateActivationToken(long accountId, String token) {
        Activations activations = this.activationsDAO.getItemByColumn(Activations.class, "token", token);
        if (activations == null) throw new AccountServiceException(Messages.VALIDATE_ACTIVATION_TOKEN, HttpStatus.NOT_FOUND);
        return activations.getAccounts().getAccountId() == accountId;
    }
}