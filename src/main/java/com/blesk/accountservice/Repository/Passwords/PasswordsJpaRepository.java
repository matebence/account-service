package com.blesk.accountservice.Repository.Passwords;

import com.blesk.accountservice.Model.Passwords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordsJpaRepository extends JpaRepository<Passwords, Long> {
}