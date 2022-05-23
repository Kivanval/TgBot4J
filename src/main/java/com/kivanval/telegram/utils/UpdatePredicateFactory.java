package com.kivanval.telegram.utils;

import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Predicate;

public final class UpdatePredicateFactory {
    private UpdatePredicateFactory() {
    }

    public static Predicate<Update> hasMessageWith(String msg) {
        return Flag.TEXT.and(upd -> upd.getMessage().getText().equalsIgnoreCase(msg));
    }

    public static Predicate<Update> isNameCallbackQuery(String name) {
        return Flag.CALLBACK_QUERY.and(upd -> upd.getCallbackQuery().getData().startsWith(name));
    }
}