package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaListedPlaceDao;
import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;
import com.kivanval.telegram.utils.HibernateUtils;

public class JpaListedPlaceRepository extends AbstractJpaRepository<ListedPlace, ListedPlaceKey> {
    public JpaListedPlaceRepository() {
        super(new JpaListedPlaceDao(HibernateUtils.getSession()));
    }

}
