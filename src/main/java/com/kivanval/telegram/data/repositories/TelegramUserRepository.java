package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.models.TelegramUser;

public interface TelegramUserRepository {
    TelegramUser get(Long id);
    void add(TelegramUser user);
    void update(TelegramUser user);
    void remove(TelegramUser user);
}
