package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsCrudRepository extends CrudRepository<Accounts, Long> {
}