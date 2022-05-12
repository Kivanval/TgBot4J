package com.kivanval.telegram.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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
        TelegramUser user = new TelegramUser();
        user.id = id;
        user.firstName = "test";
        user.isBot = false;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    public void firstNameIsNullOrEmpty(String firstName) {
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = firstName;
        user.isBot = false;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be empty", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void isBotIsNull(Boolean isBot) {
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = "test";
        user.isBot = isBot;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "testTestTestTestTestTestTestTestT", "Тестування"})
    public void userNameNotPatter(String userName) {
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = "test";
        user.isBot = false;
        user.userName = userName;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must match \"\\w{5,32}\"", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void userIsValid() {
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = "test";
        user.isBot = false;
        user.userName = "testing";
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(0, constraintViolations.size());}
}