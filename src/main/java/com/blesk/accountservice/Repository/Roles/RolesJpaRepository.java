package com.blesk.accountservice.Repository.Roles;

import com.blesk.accountservice.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesJpaRepository extends JpaRepository<Roles, Long> {
}