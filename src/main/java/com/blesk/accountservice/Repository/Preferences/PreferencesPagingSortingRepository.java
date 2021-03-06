package com.blesk.accountservice.Repository.Preferences;

import com.blesk.accountservice.Model.Preferences;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesPagingSortingRepository extends PagingAndSortingRepository<Preferences, Long> {
}