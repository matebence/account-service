package com.blesk.authorizationserver.Repository.Accounts;

import com.blesk.authorizationserver.Model.Accounts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountsPaginSortingRepository extends PagingAndSortingRepository<Accounts, Long> {
}
