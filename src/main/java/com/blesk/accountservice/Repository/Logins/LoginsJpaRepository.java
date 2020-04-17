package com.blesk.accountservice.Repository.Logins;

import com.blesk.accountservice.Model.Logins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginsJpaRepository extends JpaRepository<Logins, Long> {
}