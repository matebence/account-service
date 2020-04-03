package com.blesk.accountservice.Repository.Logins;

import com.blesk.accountservice.Model.Logins;
import org.springframework.data.repository.CrudRepository;

public interface LoginsCrudRepository extends CrudRepository<Logins, Long> {
}