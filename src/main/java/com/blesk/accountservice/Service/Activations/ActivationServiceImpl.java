package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.DAO.Activations.ActivationsDAOImpl;
import com.blesk.accountservice.Model.Activations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
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
        return this.activationsDAO.save(activations);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteActivationToken(Long activationTokenId) {
        return this.activationsDAO.delete("activations", "activation_id", activationTokenId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateActivationToken(Activations activations) {
        return this.activationsDAO.update(activations);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Activations getActivationToken(Long activationTokenId) {
        return this.activationsDAO.get(Activations.class, activationTokenId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Activations findActivationToken(String token) {
        return this.activationsDAO.getItemByColumn(Activations.class, "token", token);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Activations> getAllActivationTokens(int pageNumber, int pageSize) {
        return this.activationsDAO.getAll(Activations.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForActivationToken(HashMap<String, HashMap<String, String>> criterias) {
        return this.activationsDAO.searchBy(Activations.class, criterias);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Boolean validateActivationToken(long accountId, String token) {
        Activations activations = this.activationsDAO.getItemByColumn(Activations.class, "token", token);
        if (activations == null) return false;
        return activations.getAccounts().getAccountId() == accountId;
    }
}