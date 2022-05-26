package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;
import jakarta.persistence.EntityManager;

public class JpaListedPlaceDao extends AbstractJpaDao<ListedPlace, ListedPlaceKey> {

    public JpaListedPlaceDao(EntityManager entityManager) {
        super(entityManager, ListedPlace.class, ListedPlaceKey.class);
    }
}
