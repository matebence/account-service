package com.blesk.accountservice.DAO.Passwords;

import com.blesk.accountservice.Model.Passwords;

public interface PasswordsDAO {

    Passwords getPasswordTokenByToken(String token);
}