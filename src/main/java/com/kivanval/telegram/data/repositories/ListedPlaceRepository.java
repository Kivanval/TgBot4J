package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;

import java.util.List;

public interface ListedPlaceRepository extends Repository<ListedPlace, ListedPlaceKey> {

    List<ListedPlace> getByUserId(Long userId);

    List<ListedPlace> getByListId(Long listId);
}
