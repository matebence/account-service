package com.blesk.accountservice.Validator.Password;

import com.blesk.accountservice.Value.Messages;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class PasswordValidation implements ConstraintValidator<Password, String> {

    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(Password constraintAnnotation) {
        try {
            String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
            this.dictionaryRule = new DictionaryRule(new WordListDictionary(WordLists.createFromReader(new FileReader[]{new FileReader(invalidPasswordList)}, false, new ArraysSort())));
        } catch (IOException e) {
            throw new RuntimeException(Messages.BLACKLISTED_PASSWORDS, e);
        }
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Resource resource = new ClassPathResource("/password-rules.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
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

            RuleResult result = passwordValidator.validate(new PasswordData(password));
            if (result.isValid()) return true;
            context.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).get(0)).addConstraintViolation().disableDefaultConstraintViolation();
        } catch (IOException e) {
            throw new RuntimeException(Messages.PASSWORD_RULES, e);
        }
        return false;
    }
}