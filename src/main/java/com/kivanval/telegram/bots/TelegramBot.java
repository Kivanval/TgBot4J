package com.kivanval.telegram.bots;

import com.kivanval.telegram.abilities.GreetingAbility;
import com.kivanval.telegram.abilities.HelpAbility;
import com.kivanval.telegram.abilities.StartAbility;
import com.kivanval.telegram.constants.BotConfigConstants;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.util.AbilityExtension;

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

    public AbilityExtension replyToHelp() {
        return new HelpAbility();
    }

    public AbilityExtension replyToStart() {
        return new StartAbility();
    }

    public AbilityExtension replyToGreeting() {
        return new GreetingAbility();
    }

    protected void setSilentSender(SilentSender silent) {
        this.silent = silent;
    }

    @Override
    public long creatorId() {
        return BotConfigConstants.CREATOR_ID;
    }
}
