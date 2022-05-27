package com.kivanval.telegram.abilities;

import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.constants.AbilityConstant;
import com.kivanval.telegram.data.dao.JdbcTelegramListDao;
import com.kivanval.telegram.data.repo.JdbcTelegramListRepository;
import com.kivanval.telegram.data.repo.JdbcTelegramUserRepository;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import com.kivanval.telegram.utils.TelegramListUtils;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.kivanval.telegram.constants.BotConfigConstant.DATA_SOURCE;
import static com.kivanval.telegram.utils.UpdatePredicateFactory.hasMessageWith;
import static com.vdurmont.emoji.EmojiParser.parseToUnicode;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;


public class CreateAbility implements AbilityExtension {
    private final TelegramBot bot;

    public CreateAbility(TelegramBot bot) {
        this.bot = bot;
    }

    public static final String REPLY_GET_TITLE = """
            OK, the list has been created with name '%s'!
            You can see it on <b>/lists</b>.""";


    public static final String AUTO = "/auto";


    public ReplyFlow replyToCreate() {

        Reply replyToGetAlias = Reply.of((abilityBot, upd) -> {
                    JdbcTelegramListRepository listRepo = new JdbcTelegramListRepository(DATA_SOURCE);
                    JdbcTelegramUserRepository userRepo = new JdbcTelegramUserRepository(DATA_SOURCE);

                    TelegramUser user = TelegramUser.from(upd.getMessage().getFrom());
                    userRepo.addIfAbsent(user);


                    String title = upd.getMessage().getText();
                    title = title.equals(AUTO) ? TelegramListUtils.getAutoTitle() : title;

                    TelegramList list = new TelegramList();
                    list.setTitle(title);
                    user.addCreatedList(list);

                    listRepo.add(list);


                    bot.silent().execute(SendMessage.builder()
                            .disableWebPagePreview(true)
                            .parseMode("HTML")
                            .chatId(String.valueOf(getChatId(upd)))
                            .text(REPLY_GET_TITLE.formatted(title))
                            .build()
                    );

                },
                Predicate.not(hasMessageWith("/" + AbilityConstant.CREATE)),
                hasMessageValidateTitle()
        );

        return ReplyFlow.builder(bot.db(), bot.getReplyFlowId().incrementAndGet())
                .action((baseAbilityBot, upd) -> bot.silent().execute(
                        SendMessage.builder()
                                .disableWebPagePreview(true)
                                .parseMode("HTML")
                                .chatId(String.valueOf(getChatId(upd)))
                                .text(AbilityConstant.CREATE_REPLY)
                                .build()
                ))
                .onlyIf(hasMessageWith("/" + AbilityConstant.CREATE))
                .next(replyToGetAlias)
                .build();
    }


    public static final String REPLY_NO_VALID_TITLE = """
            Sorry, this is an unacceptable title.
            Stick to the fact that it is no longer than 255 characters.""";

    public static final String REPLY_COMMAND_TITLE = parseToUnicode("The title cannot be a command :snail:.");

    private Predicate<Update> hasMessageValidateTitle() {
        return Flag.TEXT.and(upd -> {
            String text = upd.getMessage().getText();
            if (text.equals(AUTO)) {
                return true;
            }
            if (text.isEmpty() || text.length() > 255) {
                bot.silent().send(REPLY_NO_VALID_TITLE, getChatId(upd));
                return false;
            }
            if (text.startsWith("/")) {
                bot.silent().send(REPLY_COMMAND_TITLE, getChatId(upd));
                return false;
            }
            return true;
        });
    }

}
