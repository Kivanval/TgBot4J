package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserDao extends Dao<TelegramUser, Long> {
    Optional<TelegramUser> readByUserName(String userName);

    List<TelegramUser> readManagersByListId(Long listId);

    Optional<TelegramUser> readCreatorByListId(Long listId);

    List<TelegramUser> readUsersByListId(Long listId);
}
