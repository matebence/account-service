package com.blesk.accountservice.Repository.Activations;

import com.blesk.accountservice.Model.Activations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationsJpaRepository extends JpaRepository<Activations, Long> {
}