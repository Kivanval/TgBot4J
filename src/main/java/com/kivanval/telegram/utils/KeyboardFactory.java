package com.kivanval.telegram.utils;

import com.kivanval.telegram.constants.AbilityConstant;
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

    public static final String STOP_BUTTON = "stop";
    public static final String RIGHT_BUTTON = "right";

    public static final String LEFT_BUTTON = "left";

    public static final String NONE_BUTTON = "none";

    public static final String GET_BUTTON = "get";


    public static InlineKeyboardMarkup listNavigationKeyboard(int listSize, int number) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        if (number < 1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + STOP_BUTTON)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + STOP_BUTTON)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":rewind:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + LEFT_BUTTON + " " + 0)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":arrow_backward:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + LEFT_BUTTON + " " + (number - 1))
                    .build());
        }

        if (number == -1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text("none")
                    .callbackData(AbilityConstant.MY_LISTS + "_" + NONE_BUTTON)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(String.valueOf(number + 1))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + (number + 1))
                    .build());
        }

        if (listSize < 2 || number >= listSize - 1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + STOP_BUTTON)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + STOP_BUTTON)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":arrow_forward:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + RIGHT_BUTTON + " " + (number + 1))
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":fast_forward:"))
                    .callbackData(AbilityConstant.MY_LISTS + "_" + RIGHT_BUTTON + " " + (listSize - 1))
                    .build());
        }

        keyboardsRows.add(keyboardRow);
        inlineKeyboard.setKeyboard(keyboardsRows);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup generalListInfoKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(InlineKeyboardButton.builder()
                .text("get one-by-one")
                .callbackData(AbilityConstant.MY_LISTS + "_" + GET_BUTTON)
                .build());
        keyboardsRows.add(keyboardRow);
        inlineKeyboard.setKeyboard(keyboardsRows);
        return inlineKeyboard;
    }

}
