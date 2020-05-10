package com.blesk.accountservice.Repository.Roles;

import com.blesk.accountservice.Model.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesPaginSortingRepository extends PagingAndSortingRepository<Roles, Long> {
}