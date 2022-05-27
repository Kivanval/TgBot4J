package com.kivanval.telegram.models;

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

class ListedPlaceTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    void keyIsNull(ListedPlaceKey key) {
        ListedPlace place = getValidListedNumber();
        place.id = key;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void keyNotValid() {
        ListedPlace place = getValidListedNumber();
        place.id.listId = null;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void listNotValid() {
        ListedPlace place = getValidListedNumber();
        place.list.id = null;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void userNotValid() {
        ListedPlace place = getValidListedNumber();
        place.user.id = null;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void stateInNull() {
        ListedPlace place = getValidListedNumber();
        place.state = null;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }


    @Test
    void entryDateIsNull() {
        ListedPlace place = getValidListedNumber();
        place.entryDate = null;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void entryDateInTheFuture() {
        ListedPlace place = getValidListedNumber();
        place.entryDate = LocalDateTime.MAX;
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the past or in the present", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void numberIsNull() {
        ListedPlace place = getValidListedNumber();
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void numberLessThanZero() {
        ListedPlace place = getValidListedNumber();
        Set<ConstraintViolation<ListedPlace>> constraintViolations =
                validator.validate(place);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than 0", constraintViolations.iterator().next().getMessage());
    }

    static ListedPlace getValidListedNumber() {
        ListedPlace place = new ListedPlace();
        place.id = ListedPlaceKeyTest.getValidListedNumberKey();
        place.list = TelegramListTest.getValidTelegramList();
        place.user = TelegramUserTest.getValidTelegramUser();
        place.state = ListedPlace.State.WAITING;
        place.entryDate = LocalDateTime.MIN;
        return place;
    }

}