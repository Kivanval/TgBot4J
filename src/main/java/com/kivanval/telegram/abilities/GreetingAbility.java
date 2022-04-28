package com.kivanval.telegram.abilities;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class GreetingAbility implements AbilityExtension {

    public Reply sayGreetingProtocol() {
        String msg = EmojiParser.parseToUnicode("Героям слава! :heart::ua:");
        BiConsumer<BaseAbilityBot, Update> action = (baseAbilityBot, update)
                -> baseAbilityBot.silent().execute(SendMessage.builder()
                .chatId(String.valueOf(getChatId(update)))
                .replyToMessageId(update.getMessage().getMessageId())
                .text(msg)
                .build());

        return Reply.of(action, isGreetingProtocol());
    }

    private Predicate<Update> isGreetingProtocol() {
        return (update) -> update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText()
                .matches("^[^\\p{L}]*[Сс][Лл][Аа][Вв][Аа]\\s+У[Кк][Рр][Аа][Її][Нн][Іі][^\\p{L}]*$");
    }
}
