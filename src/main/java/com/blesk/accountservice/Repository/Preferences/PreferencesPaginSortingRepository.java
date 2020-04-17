package com.blesk.accountservice.Repository.Preferences;

import com.blesk.accountservice.Model.Preferences;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PreferencesPaginSortingRepository extends PagingAndSortingRepository<Preferences, Long> {
}