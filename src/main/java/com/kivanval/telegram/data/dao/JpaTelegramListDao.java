package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;
import jakarta.persistence.EntityManager;

public class JpaTelegramListDao extends AbstractJpaDao<TelegramList, Long> {

    public JpaTelegramListDao(EntityManager entityManager) {
        super(entityManager, TelegramList.class, Long.class);
    }
}
