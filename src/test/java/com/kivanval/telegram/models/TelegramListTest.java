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
    public void idIsNull(Long id) {
        TelegramList list = getValidTelegramList();
        list.id = id;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void accessKeyIsNull(String accessKey) {
        TelegramList list = getValidTelegramList();
        list.accessKey = accessKey;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void accessKeyTooShort() {
        TelegramList list = getValidTelegramList();
        list.accessKey = "";
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 255", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void aliasTooShort() {
        TelegramList list = getValidTelegramList();
        list.alias = "";
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 1 and 255", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void stateIsNull(TelegramList.State state) {
        TelegramList list = getValidTelegramList();
        list.state = state;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void maxSizeLessThanZero(Integer maxSize) {
        TelegramList list = getValidTelegramList();
        list.maxSize = maxSize;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void startDateInFuture(LocalDateTime startDate) {
        TelegramList list = getValidTelegramList();
        list.startDate = startDate;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void startDateInTheFuture() {
        TelegramList list = getValidTelegramList();
        list.startDate = LocalDateTime.MAX;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the past or in the present", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void endDateInThePast() {
        TelegramList list = getValidTelegramList();
        list.endDate = LocalDateTime.MIN;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a future date", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void adminIsNull(TelegramUser user) {
        TelegramList list = getValidTelegramList();
        list.admin = user;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void adminIsNotValid() {
        TelegramList list = getValidTelegramList();
        list.admin.isBot = null;
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void listIsValid() {
        TelegramList list = getValidTelegramList();
        Set<ConstraintViolation<TelegramList>> constraintViolations =
                validator.validate(list);

        assertEquals(0, constraintViolations.size());
    }

    static TelegramList getValidTelegramList() {
        TelegramList list = new TelegramList();
        list.id = 10903L;
        list.accessKey = "helloworld";
        list.state = TelegramList.State.ACTIVE;
        list.maxSize = 10903;
        list.startDate = LocalDateTime.MIN;
        list.admin = TelegramUserTest.getValidTelegramUser();
        return list;
    }


}