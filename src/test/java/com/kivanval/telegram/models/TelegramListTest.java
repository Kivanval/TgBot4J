package com.kivanval.telegram.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramListTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    void idIsNull(Long id) {
        TelegramList list = getValidTelegramList();
        list.id = id;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }
    @Test
    void aliasTooShort() {
        TelegramList list = getValidTelegramList();
        list.title = "";
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 255", constraintViolations.iterator().next().getMessage());
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void maxSizeLessThanZero(Integer maxSize) {
        TelegramList list = getValidTelegramList();
        list.maxSize = maxSize;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    void startDateInFuture(LocalDateTime startDate) {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void startDateInTheFuture() {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the past or in the present", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void endDateInThePast() {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a future date", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    void creatorIsNull(TelegramUser user) {
        TelegramList list = getValidTelegramList();
        list.creator = user;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void creatorIsNotValid() {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void listIsValid() {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(0, constraintViolations.size());
    }

    static TelegramList getValidTelegramList() {
        TelegramList list = new TelegramList();
        list.id = 10903L;
        list.maxSize = 10903;
        list.creator = TelegramUserTest.getValidTelegramUser();
        return list;
    }


}