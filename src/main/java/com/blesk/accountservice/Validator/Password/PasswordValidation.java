package com.blesk.accountservice.Validator.Password;

import com.blesk.accountservice.Value.Messages;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class PasswordValidation implements ConstraintValidator<Password, String> {

    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(Password constraintAnnotation) {
        try {
            String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
            this.dictionaryRule = new DictionaryRule(new WordListDictionary(WordLists.createFromReader(new FileReader[] {new FileReader(invalidPasswordList)},false, new ArraysSort())));
        } catch (IOException e) {
            throw new RuntimeException(Messages.BLACKLISTED_PASSWORDS, e);
        }
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        try {
            URL url = this.getClass().getClassLoader().getResource("password-rules.properties");
            Properties properties = new Properties();
            properties.load(new FileInputStream(url.getPath()));

            MessageResolver messageResolver = new PropertiesMessageResolver(properties);
            PasswordValidator passwordValidator = new PasswordValidator(messageResolver, Arrays.asList(
                    new LengthRule(8, 30),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    new CharacterRule(EnglishCharacterData.Digit, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1),
                    new WhitespaceRule(),
                    this.dictionaryRule
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