package com.blesk.accountservice.Repository.Accounts;

import com.blesk.accountservice.DTO.JPQL.AccountJoinValuesByColumn;
import com.blesk.accountservice.Model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsJpaRepository extends JpaRepository<Accounts, Long> {

    @Query("SELECT new com.blesk.accountservice.DTO.JPQL.AccountJoinValuesByColumn(a.accountId, a.userName, a.email, c.name) " +
           "FROM Accounts a " +
           "INNER JOIN a.accountRoles b " +
           "INNER JOIN b.roles c " +
           "WHERE a.accountId IN ?1 " +
           "AND c.name IN ?2")
    List<AccountJoinValuesByColumn> getJoinValuesByColumn(List<Long> ids, List<String> name, String columnName);
}