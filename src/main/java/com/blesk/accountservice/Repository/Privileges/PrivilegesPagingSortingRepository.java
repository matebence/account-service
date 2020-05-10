package com.blesk.accountservice.Repository.Privileges;

import com.blesk.accountservice.Model.Privileges;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesPagingSortingRepository extends PagingAndSortingRepository<Privileges, Long> {
}