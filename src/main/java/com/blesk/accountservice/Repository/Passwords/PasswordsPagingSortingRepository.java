package com.blesk.accountservice.Repository.Passwords;

import com.blesk.accountservice.Model.Passwords;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordsPaginSortingRepository extends PagingAndSortingRepository<Passwords, Long> {
}