package com.blesk.authorizationserver.Repository.Preferences;

import com.blesk.authorizationserver.Model.Preferences.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesJpaRepository extends JpaRepository<Preferences, Long> {
}
