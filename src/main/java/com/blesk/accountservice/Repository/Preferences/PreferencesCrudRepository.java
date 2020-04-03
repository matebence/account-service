package com.blesk.authorizationserver.Repository.Preferences;

import com.blesk.authorizationserver.Model.Preferences.Preferences;
import org.springframework.data.repository.CrudRepository;

public interface PreferencesCrudRepository extends CrudRepository<Preferences, Long> {
}
