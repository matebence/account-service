package com.blesk.authorizationserver.Repository.Roles;

import com.blesk.authorizationserver.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesJpaRepository extends JpaRepository<Roles, Long> {
}
