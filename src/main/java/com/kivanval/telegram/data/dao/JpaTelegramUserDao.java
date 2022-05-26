package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramUser;
import jakarta.persistence.EntityManager;

public class JpaTelegramUserDao extends AbstractJpaDao<TelegramUser, Long> {


    protected JpaTelegramUserDao(EntityManager entityManager) {
        super(entityManager, TelegramUser.class, Long.class);
    }
}
