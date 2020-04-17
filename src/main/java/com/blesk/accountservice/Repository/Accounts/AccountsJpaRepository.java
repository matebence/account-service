package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsJpaRepository extends JpaRepository<Accounts, Long> {
}