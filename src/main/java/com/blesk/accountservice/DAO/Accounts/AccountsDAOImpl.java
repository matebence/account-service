package com.blesk.accountservice.DAO.Accounts;

import com.blesk.accountservice.DAO.DAOImpl;
import com.blesk.accountservice.Model.Accounts;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsDAOImpl extends DAOImpl<Accounts> implements AccountsDAO {
}