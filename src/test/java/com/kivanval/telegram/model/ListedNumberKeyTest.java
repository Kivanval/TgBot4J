package com.kivanval.telegram.model;

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

class ListedNumberKeyTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    public void listIdIsNull(Long listId) {
        ListedNumberKey key = getValidListedNumberKey();
        key.listId = listId;
        Set<ConstraintViolation<ListedNumberKey>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void userIdIsNull(Long userId) {
        ListedNumberKey key = getValidListedNumberKey();
        key.userId = userId;
        Set<ConstraintViolation<ListedNumberKey>> constraintViolations =
                validator.validate(key);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void ListedNumberKeyIsValid() {
        ListedNumberKey key = getValidListedNumberKey();
        Set<ConstraintViolation<ListedNumberKey>> constraintViolations =
                validator.validate(key);

        assertEquals(0, constraintViolations.size());
    }


    static ListedNumberKey getValidListedNumberKey() {
        ListedNumberKey key = new ListedNumberKey();
        key.listId = 0L;
        key.userId = 0L;
        return key;
    }
}