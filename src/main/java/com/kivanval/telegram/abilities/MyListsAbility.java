package com.kivanval.telegram.abilities;


import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.constants.AbilityConstant;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import com.kivanval.telegram.utils.KeyboardFactory;
import com.kivanval.telegram.utils.TelegramListUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyListsAbility implements AbilityExtension {
    private TelegramBot bot;

    public static final String LISTS_NAVIGATION_TITLE = parseToUnicode("<u><b>List Information</b></u>");
    public static final String EMPTY_LIST = parseToUnicode("""
            <b>List information</b>
                        
            At this point, you do not yet have your queues. :disappointed_relieved:
            You can create them with the command <b>/create</b>.
             """);


    public Ability replyToMyLists() {
        return Ability.builder()
                .name("mylists")
                .info(AbilityConstant.MY_LISTS_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(ctx -> bot.getUserRepository().update(TelegramUser.from(ctx.user())))
                .post(ctx -> {
                    List<TelegramList> list = bot.getListRepository()
                            .getByCreatorId(ctx.user().getId());
                    String replyText = String.join("\n\n", LISTS_NAVIGATION_TITLE,
                            (list.isEmpty() ? EMPTY_LIST : TelegramListUtils.getInfo(list.get(0))));
                    ctx.bot().silent().execute(SendMessage.builder()
                            .disableWebPagePreview(true)
                            .parseMode("HTML")
                            .chatId(String.valueOf(ctx.chatId()))
                            .text(replyText)
                            .replyMarkup(myListsNavigation(list))
                            .build());
                })
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            CallbackQuery query = upd.getCallbackQuery();
            String data = query.getData();
            if (data.contains(RIGHT_BUTTON)) {
                int number = Integer.parseInt(data.split(" ")[1]);
            }

        };
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public static String STOP_BUTTON = "stop";
    public static String RIGHT_BUTTON = "right";

    public ReplyKeyboard myListsNavigation(List<TelegramList> list) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(InlineKeyboardButton.builder()
                .text(parseToUnicode(":black_square_for_stop:"))
                .callbackData(STOP_BUTTON)
                .build());
        if (list.size() < 2) {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":black_square_for_stop:"))
                    .callbackData(STOP_BUTTON)
                    .build());
        } else {
            keyboardRow.add(InlineKeyboardButton.builder()
                    .text(parseToUnicode(":arrow_right:"))
                    .callbackData(RIGHT_BUTTON + " 2")
                    .build());
        }
        keyboardsRows.add(keyboardRow);
        inlineKeyboard.setKeyboard(keyboardsRows);
        return inlineKeyboard;
    }
}
