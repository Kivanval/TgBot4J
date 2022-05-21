package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstant;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class GreetingAbility implements AbilityExtension {

    public static final String GREETING_REPLY = parseToUnicode("Героям слава! :heart::ua:");

    public Reply replyToGreeting() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> bot.silent()
                .execute(SendMessage.builder()
                        .chatId(String.valueOf(getChatId(upd)))
                        .replyToMessageId(upd.getMessage().getMessageId())
                        .text(AbilityConstant.GREETING_REPLY)
                        .build()
                );

        return Reply.of(action, Flag.TEXT, isGreetingProtocol());
    }

    private static final String GREETING_REGEX = "^(?iu)[^\\p{L}]*СЛАВА\\s+УКРАЇНІ[^\\p{L}]*$";

    private Predicate<Update> isGreetingProtocol() {
        return update ->
                Pattern.compile(GREETING_REGEX, Pattern.CANON_EQ)
                        .matcher(update.getMessage().getText())
                        .matches();
    }


}
