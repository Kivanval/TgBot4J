package com.kivanval.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public final class KeyboardFactory {
    private KeyboardFactory() {
    }

    public static final String NO_BUTTON = "No";
    public static final String YES_BUTTON = "Yes";

    public static ReplyKeyboard getChoiceKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(YES_BUTTON);
        keyboardRow.add(NO_BUTTON);
        keyboardRows.add(keyboardRow);
        replyKeyboard.setKeyboard(keyboardRows);
        return replyKeyboard;
    }

}
