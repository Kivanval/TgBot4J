package com.kivanval.telegram.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TelegramListTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    public void IdIsNull(Long id) {
        TelegramList list = new TelegramList();
        list.id = id;
        list.maxSize = 10903;
        list.startDate = LocalDateTime.MIN;
        TelegramUser user = new TelegramUser();
        user.id = 10903L;
        user.firstName = "test";
        user.isBot = false;
        user.userName = "testing";
        list.admin = user;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }


}