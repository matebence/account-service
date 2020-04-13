package com.blesk.accountservice.DAO.Activations;

import com.blesk.accountservice.Model.Activations;

public interface ActivationsDAO {

    Activations gettActivationToken(String token);
}