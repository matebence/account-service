package com.blesk.accountservice.Repository.Activations;

import com.blesk.accountservice.Model.Activations;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationsPaginSortingRepository extends PagingAndSortingRepository<Activations, Long> {
}