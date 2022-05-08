package com.kivanval.telegram.bots;

import com.kivanval.telegram.abilities.HelpAbility;
import com.kivanval.telegram.abilities.StartAbility;
import com.kivanval.telegram.constants.AbilityConstants;
import com.kivanval.telegram.constants.BotConfigConstants;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;


public class TelegramBot extends AbilityBot {

    TelegramBot(DBContext dbContext) {
        super(BotConfigConstants.BOT_TOKEN, BotConfigConstants.BOT_USERNAME, dbContext);
    }

    public TelegramBot() {
        super(BotConfigConstants.BOT_TOKEN, BotConfigConstants.BOT_USERNAME);
    }

    public TelegramBot(String token, String username) {
        super(token, username, BotConfigConstants.ABILITY_TOGGLE);
    }

    public TelegramBot(String token, String username, AbilityToggle toggle) {
        super(token, username, toggle);
    }

    public Reply replyToGreeting() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, update) -> bot.silent()
                .execute(SendMessage.builder()
                        .chatId(String.valueOf(getChatId(update)))
                        .replyToMessageId(update.getMessage().getMessageId())
                        .text(AbilityConstants.GREETING_REPLY)
                        .build()
                );

        return Reply.of(action, Flag.MESSAGE, Flag.TEXT, isGreetingProtocol());
    }

    private Predicate<Update> isGreetingProtocol() {
        return (update) -> update.getMessage().getText()
                .matches("^(?iu)[^\\p{L}]*СЛАВА\\s+УКРАЇНІ[^\\p{L}]*$");
    }

    public AbilityExtension replyToHelp() {
        return new HelpAbility();
    }

    public AbilityExtension replyToStart() {
        return new StartAbility();
    }


    public void setSilentSender(SilentSender silent) {
        this.silent = silent;
    }

    @Override
    public long creatorId() {
        return BotConfigConstants.CREATOR_ID;
    }
}
