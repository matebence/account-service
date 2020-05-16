package com.blesk.accountservice.Repository.Activations;

import com.blesk.accountservice.Model.Activations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationsCrudRepository extends CrudRepository<Activations, Long> {
}