package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;

import java.util.List;
import java.util.Optional;

public interface TelegramListDao extends Dao<TelegramList, Long> {

    Optional<TelegramList> readByAlias(String alias);

    List<TelegramList> readByManagerId(Long managerId);

    List<TelegramList> readByUserId(Long userId);

    List<TelegramList> readByCreatorId(Long creatorId);
}
