package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.DAO.Activations.ActivationsDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Activations;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (this.activationsDAO.save(activations) == null)
            throw new AccountServiceException(Messages.CREATE_ACTIVATION_TOKEN);
        return activations;
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
    public Boolean validateActivationToken(long accountId, String token) {
        Activations activations = this.activationsDAO.gettActivationToken(token);
        if (activations == null)
            throw new AccountServiceException(Messages.VALIDATE_ACTIVATION_TOKEN);

        return activations.getAccount().getAccountId() == accountId;
    }
}
