package com.blesk.accountservice.Repository.Passwords;

import com.blesk.accountservice.Model.Passwords;
import org.springframework.data.repository.CrudRepository;

public interface PasswordsCrudRepository extends CrudRepository<Passwords, Long> {
}