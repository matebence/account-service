package com.blesk.accountservice.Repository.Preferences;

import com.blesk.accountservice.Model.Preferences.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesJpaRepository extends JpaRepository<Preferences, Long> {
}
