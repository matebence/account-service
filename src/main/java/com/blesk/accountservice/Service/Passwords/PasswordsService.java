package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.Model.Passwords;

public interface PasswordsService {

    Passwords createPasswordToken(Passwords passwords);

    Boolean deletePasswordToken(Long passwordResetTokenId);

    Boolean updatePasswordToken(Passwords passwords);

    Passwords getPasswordToken(Long passwordResetTokenId);

    Boolean validatePasswordToken(long accountId, String token);
}