package com.kivanval.telegram.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TelegramUserTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    public void IdIsNull(Long id) {
        TelegramUser user = getValidTelegramUser();
        user.id = id;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void firstNameIsNull(String firstName) {
        TelegramUser user = getValidTelegramUser();
        user.firstName = firstName;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void firstNameTooShort() {
        TelegramUser user = getValidTelegramUser();
        user.firstName = "";
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 255", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void isBotIsNull(Boolean isBot) {
        TelegramUser user = getValidTelegramUser();
        user.isBot = isBot;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "testTestTestTestTestTestTestTestT", "Тестування"})
    public void userNameNotPatter(String userName) {
        TelegramUser user = getValidTelegramUser();
        user.userName = userName;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must match \"\\w{5,32}\"", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void userIsValid() {
        TelegramUser user = getValidTelegramUser();
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(0, constraintViolations.size());
    }

    static TelegramUser getValidTelegramUser() {
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = "test";
        user.isBot = false;
        user.userName = "testing";
        return user;
    }
}