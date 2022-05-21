package com.kivanval.telegram.bot;

import com.kivanval.telegram.abilities.*;
import com.kivanval.telegram.constants.BotConfigConstant;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.Var;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Getter
@Setter

public class TelegramBot extends AbilityBot {

    private TelegramUserRepository userRepository;
    private TelegramListRepository listRepository;

    TelegramBot(DBContext dbContext) {
        super(BotConfigConstant.BOT_TOKEN, BotConfigConstant.BOT_USERNAME, dbContext);
    }

    public TelegramBot(TelegramUserRepository userRepository, TelegramListRepository listRepository) {
        super(BotConfigConstant.BOT_TOKEN, BotConfigConstant.BOT_USERNAME);
        this.db.getVar("ReplyFlowId").set(0);
        this.userRepository = userRepository;
        this.listRepository = listRepository;
    }

    public TelegramBot(String token, String username) {
        super(token, username, BotConfigConstant.ABILITY_TOGGLE);
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

    public AbilityExtension replyToCreate() {
        return new CreateAbility(this);
    }

    public AbilityExtension replyToMyLists() {
        return new MyListsAbility(this);
    }

    protected void setSilentSender(SilentSender silent) {
        this.silent = silent;
    }


    @Override
    public long creatorId() {
        return BotConfigConstant.CREATOR_ID;
    }
}
