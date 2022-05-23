package com.kivanval.telegram.abilities;


import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.constants.AbilityConstant;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import com.kivanval.telegram.utils.HibernateUtils;
import com.kivanval.telegram.utils.KeyboardFactory;
import com.kivanval.telegram.utils.TelegramListUtils;
import org.hibernate.Session;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

import static com.kivanval.telegram.utils.KeyboardFactory.*;
import static com.kivanval.telegram.utils.UpdatePredicateFactory.isNameCallbackQuery;
import static com.vdurmont.emoji.EmojiParser.parseToUnicode;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class MyListsAbility implements AbilityExtension {
    private final TelegramBot bot;

    public MyListsAbility(TelegramBot bot) {
        this.bot = bot;
    }

    public static final String EMPTY_LIST = parseToUnicode("""
            At this point, you do not yet have your queues. :disappointed_relieved:
            You can create them with the command <b>/create</b>.
             """);

    public static final String GENERAL_LISTS_TITLE = parseToUnicode("<u><b>General List Information</b></u>");
    public Ability replyToMyLists() {
        return Ability.builder()
                .name("mylists")
                .info(AbilityConstant.MY_LISTS_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(ctx -> {
                    Session session = HibernateUtils.getSession();
                    try (TelegramListRepository listRepository = TelegramListRepository.jpaInstance(session);
                         TelegramUserRepository userRepository = TelegramUserRepository.jpaInstance(session)) {

                        userRepository.update(TelegramUser.from(ctx.user()));

                        List<TelegramList> lists = listRepository
                                .getByCreatorId(ctx.user().getId());
                        String replyText = GENERAL_LISTS_TITLE + "\n\n" +
                                (lists.isEmpty() ? EMPTY_LIST : TelegramListUtils.getInfo(lists));
                        ctx.bot().silent().execute(SendMessage.builder()
                                .disableWebPagePreview(true)
                                .parseMode("HTML")
                                .chatId(String.valueOf(ctx.chatId()))
                                .text(replyText)
                                .replyMarkup(generalListInfoKeyboard())
                                .build());
                    }
                })
                .build();
    }

    public Reply replyToButtons() {
        return Reply.of((abilityBot, upd) -> {
                    Session session = HibernateUtils.getSession();
                    try (TelegramListRepository listRepository = TelegramListRepository.jpaInstance(session)) {

                        CallbackQuery query = upd.getCallbackQuery();
                        String data = query.getData();
                        if (data.contains(RIGHT) || data.contains(LEFT)) {
                            int number = Integer.parseInt(data.split(" ")[1]);
                            List<TelegramList> lists = listRepository
                                    .getByCreatorIdOrderByStartDate(query.getFrom().getId());
                            String replyText = TelegramListUtils.getInfo(lists.get(number));
                            bot.silent().execute(EditMessageText.builder()
                                    .disableWebPagePreview(true)
                                    .parseMode("HTML")
                                    .chatId(String.valueOf(getChatId(upd)))
                                    .text(replyText)
                                    .messageId(query.getMessage().getMessageId())
                                    .replyMarkup(KeyboardFactory.listNavigationKeyboard(lists.size(), number))
                                    .build());
                        } else if (data.contains(ONE_BY_ONE)) {
                            List<TelegramList> lists = listRepository
                                    .getByCreatorIdOrderByStartDate(query.getFrom().getId());
                            String replyText = (lists.isEmpty() ?
                                    EMPTY_LIST : TelegramListUtils.getInfo(lists.get(0)));
                            bot.silent().execute(SendMessage.builder()
                                    .disableWebPagePreview(true)
                                    .parseMode("HTML")
                                    .chatId(String.valueOf(getChatId(upd)))
                                    .text(replyText)
                                    .replyMarkup(KeyboardFactory.listNavigationKeyboard(lists.size(), 0))
                                    .build());
                        }

                        bot.silent().execute(AnswerCallbackQuery.builder()
                                .callbackQueryId(query.getId())
                                .build());
                    }
                },
                isNameCallbackQuery(AbilityConstant.MY_LISTS));
    }
}
