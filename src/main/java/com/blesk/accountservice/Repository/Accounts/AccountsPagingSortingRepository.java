package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsPagingSortingRepository extends PagingAndSortingRepository<Accounts, Long> {
}