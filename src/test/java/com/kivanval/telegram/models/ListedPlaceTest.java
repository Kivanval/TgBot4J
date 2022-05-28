package com.kivanval.telegram.models;

import com.kivanval.telegram.data.repositories.JpaListedPlaceRepository;
import com.kivanval.telegram.utils.HibernateUtils;
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

    @Test
    void insertTest() {
        JpaListedPlaceRepository listedPlaceRepository = new JpaListedPlaceRepository(HibernateUtils.getSession());
    }

}