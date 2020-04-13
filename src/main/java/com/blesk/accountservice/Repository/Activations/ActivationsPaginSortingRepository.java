package com.blesk.accountservice.Repository.Activations;

import com.blesk.accountservice.Model.Activations;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivationsPaginSortingRepository extends PagingAndSortingRepository<Activations, Long> {
}
