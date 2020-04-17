package com.blesk.accountservice.Repository.Logins;

import com.blesk.accountservice.Model.Logins;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoginsPaginSortingRepository extends PagingAndSortingRepository<Logins, Long> {
}