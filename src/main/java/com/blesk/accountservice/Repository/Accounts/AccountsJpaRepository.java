package com.blesk.authorizationserver.Repository.Accounts;

import com.blesk.authorizationserver.Model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsJpaRepository extends JpaRepository<Accounts, Long> {
}
