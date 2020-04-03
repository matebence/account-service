package com.blesk.authorizationserver.Repository.Preferences;

import com.blesk.authorizationserver.Model.Preferences.Preferences;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PreferencesPaginSortingRepository extends PagingAndSortingRepository<Preferences, Long> {
}
