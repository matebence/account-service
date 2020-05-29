package com.blesk.accountservice.Service.Activations;

import com.blesk.accountservice.Model.Activations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ActivationService {

    Activations createActivationToken(Activations activations);

    Boolean deleteActivationToken(Long activationTokenId);

    Boolean updateActivationToken(Activations activations);

    Activations getActivationToken(Long activationTokenId);

    Activations findActivationToken(String token);

    List<Activations> getAllActivationTokens(int pageNumber, int pageSize);

    Map<String, Object> searchForActivationToken(HashMap<String, HashMap<String, String>> criterias);

    Boolean validateActivationToken(long accountId, String token);
}