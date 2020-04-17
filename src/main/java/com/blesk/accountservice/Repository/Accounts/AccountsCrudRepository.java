package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountsCrudRepository extends CrudRepository<Accounts, Long> {
}