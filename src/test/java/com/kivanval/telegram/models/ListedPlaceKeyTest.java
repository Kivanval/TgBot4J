package com.kivanval.telegram.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListedPlaceKeyTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    public void listIdIsNull(Long listId) {
        ListedPlaceKey key = getValidListedNumberKey();
        key.listId = listId;
        Set<ConstraintViolation<ListedPlaceKey>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void userIdIsNull(Long userId) {
        ListedPlaceKey key = getValidListedNumberKey();
        key.userId = userId;
        Set<ConstraintViolation<ListedPlaceKey>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void listedNumberKeyIsValid() {
        ListedPlaceKey key = getValidListedNumberKey();
        Set<ConstraintViolation<ListedPlaceKey>> constraintViolations =
                validator.validate(key);

        assertEquals(0, constraintViolations.size());
    }

    static ListedPlaceKey getValidListedNumberKey() {
        ListedPlaceKey key = new ListedPlaceKey();
        key.listId = 0L;
        key.userId = 0L;
        return key;
    }
}