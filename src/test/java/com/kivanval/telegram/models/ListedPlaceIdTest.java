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

class ListedPlaceIdTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    void listIdIsNull(Long listId) {
        ListedPlaceId key = getValidListedNumberKey();
        key.listId = listId;
        Set<ConstraintViolation<ListedPlaceId>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    void userIdIsNull(Long userId) {
        ListedPlaceId key = getValidListedNumberKey();
        key.userId = userId;
        Set<ConstraintViolation<ListedPlaceId>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void listedNumberKeyIsValid() {
        ListedPlaceId key = getValidListedNumberKey();
        Set<ConstraintViolation<ListedPlaceId>> constraintViolations =
                validator.validate(key);

        assertEquals(0, constraintViolations.size());
    }

    static ListedPlaceId getValidListedNumberKey() {
        ListedPlaceId key = new ListedPlaceId();
        key.listId = 0L;
        key.userId = 0L;
        return key;
    }
}