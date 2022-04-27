package com.kivanval.telegram.bots;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.BareboneToggle;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class TelegramBot extends AbilityBot {
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_USERNAME = System.getenv("BOT_USERNAME");

    private static final BareboneToggle TOGGLE = new BareboneToggle();

    public TelegramBot() {
        super(BOT_TOKEN, BOT_USERNAME, TOGGLE);
    }

    public TelegramBot(String token, String username) {
        super(token, username, TOGGLE);
    }

    public TelegramBot(String token, String username, AbilityToggle toggle) {
        super(token, username, toggle);
    }

    public Reply sayGreetingProtocol() {
        String message = EmojiParser.parseToUnicode("Героям слава! :heart::ua:");
        BiConsumer<BaseAbilityBot, Update> action = (baseAbilityBot, update)
                -> silent.send(message, getChatId(update));
        return Reply.of(action, isGreetingProtocol());
    }

    private Predicate<Update> isGreetingProtocol() {
        return (update)
                -> update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText()
                .replaceAll("[\\s\\p{P}]+", " ")
                .strip()
                .matches("^[^\\p{L}]*[Сс][Лл][Аа][Вв][Аа] У[Кк][Рр][Аа][Її][Нн][Іі][^\\p{L}]*$");
    }

    @Override
    public long creatorId() {
        return 932231787;
    }
}
