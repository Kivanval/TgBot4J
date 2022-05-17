package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;

import java.util.List;

public interface ListedPlaceDao extends Dao<ListedPlace, ListedPlaceKey> {
    List<ListedPlace> readByUserId(Long userId);

    List<ListedPlace> readByListId(Long listId);
}
