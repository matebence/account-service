package com.blesk.accountservice.Service.Passwords;

import com.blesk.accountservice.Model.Passwords;

public interface PasswordsService {

    Passwords createPasswordToken(Passwords passwords);

    boolean deletePasswordToken(Long passwordResetTokenId);

    boolean updatePasswordToken(Passwords passwords);

    Passwords getPasswordToken(Long passwordResetTokenId);

    boolean validatePasswordResetToken(long accountId, String token);
}