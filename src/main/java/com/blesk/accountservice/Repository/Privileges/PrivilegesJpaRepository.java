package com.blesk.accountservice.Repository.Privileges;

import com.blesk.accountservice.Model.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesJpaRepository extends JpaRepository<Privileges, Long> {
}