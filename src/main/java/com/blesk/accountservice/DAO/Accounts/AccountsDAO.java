package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAO;
import com.blesk.accountservice.DTO.AccountsJoin;
import com.blesk.accountservice.Model.Accounts;

import java.util.List;

public interface AccountsDAO extends DAO<Accounts> {

    List<AccountsJoin> getJoinValuesByColumn(List<Long> ids, List<String> roles, String columName);
}