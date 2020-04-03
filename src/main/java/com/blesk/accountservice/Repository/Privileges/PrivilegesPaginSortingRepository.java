package com.blesk.accountservice.Repository.Privileges;

import com.blesk.accountservice.Model.Privileges;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PrivilegesPaginSortingRepository extends PagingAndSortingRepository<Privileges, Long> {
}