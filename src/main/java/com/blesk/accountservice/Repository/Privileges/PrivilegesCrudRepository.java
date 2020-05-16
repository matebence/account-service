package com.blesk.accountservice.Repository.Privileges;

import com.blesk.accountservice.Model.Privileges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesCrudRepository extends CrudRepository<Privileges, Long> {
}