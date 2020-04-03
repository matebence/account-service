package com.blesk.authorizationserver.Repository.Logins;

import com.blesk.authorizationserver.Model.Logins;
import org.springframework.data.repository.CrudRepository;

public interface LoginsCrudRepository extends CrudRepository<Logins, Long> {
}