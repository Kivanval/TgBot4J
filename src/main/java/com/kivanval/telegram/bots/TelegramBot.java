package com.kivanval.telegram.bots;

import com.kivanval.telegram.abilities.GreetingAbility;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;
import org.telegram.abilitybots.api.util.AbilityExtension;

public class TelegramBot extends AbilityBot {
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_USERNAME = System.getenv("BOT_USERNAME");

    private static final AbilityToggle TOGGLE = new DefaultToggle();

    public TelegramBot() {
        super(BOT_TOKEN, BOT_USERNAME, TOGGLE);
    }

    public TelegramBot(String token, String username) {
        super(token, username, TOGGLE);
    }

    public TelegramBot(String token, String username, AbilityToggle toggle) {
        super(token, username, toggle);
    }

    public AbilityExtension sayGreetingProtocol() {
        return new GreetingAbility();
    }

    @Override
    public long creatorId() {
        return 932231787;
    }
}
