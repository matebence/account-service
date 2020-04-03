package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountsPaginSortingRepository extends PagingAndSortingRepository<Accounts, Long> {
}
