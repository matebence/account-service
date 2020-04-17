package com.blesk.accountservice.Repository.Roles;

import com.blesk.accountservice.Model.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RolesPaginSortingRepository extends PagingAndSortingRepository<Roles, Long> {
}