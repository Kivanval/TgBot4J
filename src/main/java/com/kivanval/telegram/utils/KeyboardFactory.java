package com.kivanval.telegram.utils;

import com.kivanval.telegram.constants.AbilityConstant;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public final class KeyboardFactory {
    private KeyboardFactory() {
    }

    public static ReplyKeyboard getChoiceKeyboard(Collection<String> names) {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        names.forEach(keyboardRow::add);
        keyboardRows.add(keyboardRow);
        replyKeyboard.setKeyboard(keyboardRows);
        replyKeyboard.setResizeKeyboard(true);
        return replyKeyboard;
    }

    public static final String STOP = "stop";
    public static final String RIGHT = "right";

    public static final String LEFT = "left";

    public static final String NONE = "none";


    public static InlineKeyboardMarkup listNavigationKeyboard(String prefix, int listSize, int number) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        if (number < 1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(prefix + "_" + STOP)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(prefix + "_" + STOP)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":rewind:"))
                    .callbackData(prefix + "_" + LEFT + " " + 0)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":arrow_backward:"))
                    .callbackData(prefix + "_" + LEFT + " " + (number - 1))
                    .build());
        }

        if (number == -1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text("none")
                    .callbackData(prefix + "_" + NONE)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(String.valueOf(number + 1))
                    .callbackData(prefix + "_" + (number + 1))
                    .build());
        }

        if (listSize < 2 || number >= listSize - 1) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(prefix + "_" + STOP)
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(prefix + "_" + STOP)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":arrow_forward:"))
                    .callbackData(prefix + "_" + RIGHT + " " + (number + 1))
                    .build());
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":fast_forward:"))
                    .callbackData(prefix + "_" + RIGHT + " " + (listSize - 1))
                    .build());
        }

        keyboardsRows.add(keyboardRow);
        inlineKeyboard.setKeyboard(keyboardsRows);
        return inlineKeyboard;
    }

    public static final String ONE_BY_ONE = "one-by-one";

    public static InlineKeyboardMarkup generalListInfoKeyboard(String prefix) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(InlineKeyboardButton.builder()
                .text("one-by-one")
                .callbackData(prefix + "_" + ONE_BY_ONE)
                .build());
        keyboardsRows.add(keyboardRow);
        inlineKeyboard.setKeyboard(keyboardsRows);
        return inlineKeyboard;
    }

}
