package com.blesk.authorizationserver.Repository.Logins;

import com.blesk.authorizationserver.Model.Logins;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoginsPaginSortingRepository extends PagingAndSortingRepository<Logins, Long> {
}
