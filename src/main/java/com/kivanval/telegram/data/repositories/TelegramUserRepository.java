package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserRepository extends Repository<TelegramUser, Long> {
    Optional<TelegramUser> getByUserName(String userName);

    List<TelegramUser> getManagersByListId(Long listId);

    Optional<TelegramUser> getCreatorByListId(Long listId);

    List<TelegramUser> getUsersByListId(Long listId);
}
