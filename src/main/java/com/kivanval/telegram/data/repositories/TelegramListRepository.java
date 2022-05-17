package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.models.TelegramList;

import java.util.List;
import java.util.Optional;

public interface TelegramListRepository extends Repository<TelegramList, Long> {
    Optional<TelegramList> getByAlias(String alias);

    List<TelegramList> getByManagerId(Long managerId);

    List<TelegramList> getByUserId(Long userId);

    List<TelegramList> getByCreatorId(Long creatorId);
}
