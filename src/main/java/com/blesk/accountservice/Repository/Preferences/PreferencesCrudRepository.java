package com.blesk.accountservice.Repository.Preferences;

import com.blesk.accountservice.Model.Preferences;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesCrudRepository extends CrudRepository<Preferences, Long> {
}