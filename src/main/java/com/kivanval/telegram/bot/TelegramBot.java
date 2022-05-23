package com.kivanval.telegram.bot;

import com.kivanval.telegram.abilities.*;
import com.kivanval.telegram.constants.BotConfigConstant;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.Var;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.ZonedDateTime;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Getter
@Setter

public class TelegramBot extends AbilityBot {

    private AtomicInteger replyFlowId = new AtomicInteger();

    TelegramBot(DBContext dbContext) {
        super(BotConfigConstant.BOT_TOKEN, BotConfigConstant.BOT_USERNAME, dbContext);
    }

    public TelegramBot() {
        super(BotConfigConstant.BOT_TOKEN, BotConfigConstant.BOT_USERNAME);
    }

    public TelegramBot(String token, String username) {
        super(token, username, BotConfigConstant.ABILITY_TOGGLE);
    }

    public TelegramBot(String token, String username, AbilityToggle toggle) {
        super(token, username, toggle);
    }

    @Override
    public void onUpdateReceived(Update update) {
        ForkJoinTask.adapt(() -> super.onUpdateReceived(update)).fork();
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
