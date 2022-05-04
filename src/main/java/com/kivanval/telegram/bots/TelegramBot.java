package com.kivanval.telegram.bots;

import com.kivanval.telegram.abilities.GreetingAbility;
import com.kivanval.telegram.abilities.HelpAbility;
import com.kivanval.telegram.abilities.StartAbility;
import com.kivanval.telegram.constants.BotConfigConstants;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.toggle.AbilityToggle;


public class TelegramBot extends AbilityBot {

    public TelegramBot() {
        this(BotConfigConstants.BOT_TOKEN, BotConfigConstants.BOT_USERNAME);
    }

    public TelegramBot(String token, String username) {
        this(token, username, BotConfigConstants.ABILITY_TOGGLE);
    }

    public TelegramBot(String token, String username, AbilityToggle toggle) {
        super(token, username, toggle);
        addExtensions(new GreetingAbility(), new StartAbility(), new HelpAbility());
    }

    @Override
    public long creatorId() {
        return BotConfigConstants.CREATOR_ID;
    }
}
