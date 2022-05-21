package com.kivanval.telegram.utils;

import com.kivanval.telegram.models.TelegramUser;

import java.util.Objects;

public class TelegramUserUtils {

    private TelegramUserUtils() {
    }

    public static String getName(TelegramUser user) {
        return Objects.requireNonNullElse(
                user.getUserName(), user.getFirstName() +
                        Objects.requireNonNullElse(
                                user.getLastName(), "")
        );
    }
}
