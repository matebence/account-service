package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.Model.Activations;

public interface ActivationService {

    Activations createActivationToken(Activations activations);

    Boolean deleteActivationToken(Long activationTokenId);

    Boolean updateActivationToken(Activations activations);

    Activations getActivationToken(Long activationTokenId);

    Boolean validateActivationToken(long accountId, String token);
}