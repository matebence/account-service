package com.blesk.accountservice.Repository.Preferences;

import com.blesk.accountservice.Model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesJpaRepository extends JpaRepository<Preferences, Long> {
}