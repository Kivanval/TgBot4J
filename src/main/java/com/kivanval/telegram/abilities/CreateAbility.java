package com.kivanval.telegram.abilities;

import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.constants.AbilityConstant;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.Var;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAbility implements AbilityExtension {
    private TelegramBot bot;

    public static final String REPLY_GET_ALIAS = """
            OK, the list has been created!
            You can see it on <b>/mylists</b>.""";


    public static final String EMPTY = "/empty";

    public ReplyFlow replyToCreate() {
        final Var<Integer> id = bot.db().getVar("ReplyFlowId");

        Reply replyToGetAlias = Reply.of((abilityBot, upd) -> {
                    TelegramUser user = TelegramUser.from(upd.getMessage().getFrom());

                    String alias = upd.getMessage().getText();
                    alias = alias.equals(EMPTY) ? null : alias;

                    TelegramList list = new TelegramList();
                    list.setAlias(alias);
                    list.setCreator(user);

                    bot.getListRepository().update(list);

                    bot.silent().execute(SendMessage.builder()
                            .disableWebPagePreview(true)
                            .parseMode("HTML")
                            .chatId(String.valueOf(getChatId(upd)))
                            .text(REPLY_GET_ALIAS)
                            .build()
                    );

                },
                Flag.TEXT,
                Predicate.not(hasMessageWith("/" + AbilityConstant.CREATE)),
                hasMessageValidateAlias(),
                uniqueAlias()
        );

        int value = id.get();
        id.set(value + 1);
        return ReplyFlow.builder(bot.db(), value)
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

    private static Predicate<Update> hasMessageWith(String msg) {
        return Flag.TEXT.and(upd -> upd.getMessage().getText().equalsIgnoreCase(msg));
    }


    public static final String REPLY_NO_VALIDATE_ALIAS = """
            Sorry, this is an unacceptable alias.
            Stick to the fact that it is no longer than 255 characters.""";

    private Predicate<Update> hasMessageValidateAlias() {
        return Flag.TEXT.and(upd -> {
            String text = upd.getMessage().getText();
            if (text.equals(EMPTY)) {
                return true;
            }
            if (text.isEmpty() || text.length() > 255) {
                bot.silent().send(REPLY_NO_VALIDATE_ALIAS, getChatId(upd));
                return false;
            }
            return true;
        });
    }

    public static final String REPLY_NO_UQ_ALIAS = "Sorry, that name is already taken. Try another one.";

    private Predicate<Update> uniqueAlias() {
        return upd -> {
            Optional<TelegramList> list = bot.getListRepository().getByAlias(upd.getMessage().getText());
            if (list.isPresent()) {
                bot.silent().send(REPLY_NO_UQ_ALIAS, getChatId(upd));
            }
            return list.isEmpty();
        };
    }

}
