package com.blesk.accountservice.Repository.Roles;

import com.blesk.accountservice.Model.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesCrudRepository extends CrudRepository<Roles, Long> {
}