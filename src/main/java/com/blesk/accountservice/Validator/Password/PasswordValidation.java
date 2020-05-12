package com.blesk.accountservice.Validator.Password;

import com.blesk.accountservice.Value.Messages;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class PasswordValidation implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("password-rules.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            MessageResolver messageResolver = new PropertiesMessageResolver(properties);
            PasswordValidator passwordValidator = new PasswordValidator(messageResolver, Arrays.asList(
                    new LengthRule(8, 30),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    new CharacterRule(EnglishCharacterData.Digit, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1),
                    new WhitespaceRule()
            ));

            RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
            if (ruleResult.isValid()) return true;
            context.buildConstraintViolationWithTemplate(passwordValidator.getMessages(ruleResult).get(0)).addConstraintViolation().disableDefaultConstraintViolation();
        } catch (IOException e) {
            throw new RuntimeException(Messages.PASSWORD_RULES, e);
        }
        return false;
    }
}