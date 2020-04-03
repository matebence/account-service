package com.blesk.authorizationserver.Repository.Roles;

import com.blesk.authorizationserver.Model.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RolesPaginSortingRepository extends PagingAndSortingRepository<Roles, Long> {
}
