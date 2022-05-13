package com.kivanval.telegram.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListedNumberTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    public void KeyIsNull(ListedNumberKey key) {
        ListedNumber number = getValidListedNumber();
        number.id = key;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void keyNotValid() {
        ListedNumber number = getValidListedNumber();
        number.id.listId = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void listNotValid() {
        ListedNumber number = getValidListedNumber();
        number.list.id = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void userNotValid() {
        ListedNumber number = getValidListedNumber();
        number.user.id = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void stateInNull() {
        ListedNumber number = getValidListedNumber();
        number.state = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }


    @Test
    public void entryDateIsNull() {
        ListedNumber number = getValidListedNumber();
        number.entryDate = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void entryDateInTheFuture() {
        ListedNumber number = getValidListedNumber();
        number.entryDate = LocalDateTime.MAX;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the past or in the present", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void numberIsNull() {
        ListedNumber number = getValidListedNumber();
        number.number = null;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void numberLessThanZero() {
        ListedNumber number = getValidListedNumber();
        number.number = 0;
        Set<ConstraintViolation<ListedNumber>> constraintViolations =
                validator.validate(number);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }




    static ListedNumber getValidListedNumber() {
        ListedNumber number = new ListedNumber();
        number.id = ListedNumberKeyTest.getValidListedNumberKey();
        number.list = TelegramListTest.getValidTelegramList();
        number.user = TelegramUserTest.getValidTelegramUser();
        number.state = ListedNumber.State.WAITING;
        number.entryDate = LocalDateTime.MIN;
        number.number = 1;
        return number;
    }

}