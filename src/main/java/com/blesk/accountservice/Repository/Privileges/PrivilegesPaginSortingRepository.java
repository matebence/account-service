package com.blesk.authorizationserver.Repository.Privileges;

import com.blesk.authorizationserver.Model.Privileges;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PrivilegesPaginSortingRepository extends PagingAndSortingRepository<Privileges, Long> {
}