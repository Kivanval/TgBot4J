package com.kivanval.telegram.data.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateUtilsTest {

    @Test
    void getSession() {
        assertNotNull(HibernateUtils.getSession());
    }
}