package com.kivanval.telegram.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class TelegramUserTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void IdIsNull() {
        TelegramUser user = new TelegramUser();
        user.id = null;
        user.firstName = "test";
        user.isBot = false;
        Set<ConstraintViolation<TelegramUser>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @AfterEach
    void tearDown() {
    }
}