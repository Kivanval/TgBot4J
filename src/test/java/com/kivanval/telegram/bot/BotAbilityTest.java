package com.kivanval.telegram.bot;

import com.kivanval.telegram.constants.AbilityConstant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import static org.junit.jupiter.api.Assertions.*;

class BotAbilityTest {
    private static TelegramBot bot;

    private static SilentSender silent;

    @BeforeAll
    static public void setUpBot() {
        DBContext db = MapDBContext.offlineInstance("test");
        bot = new TelegramBot(db);
        bot.onRegister();
    }

    @BeforeEach
    public void setUpSender() {
        silent = Mockito.mock(SilentSender.class);
        bot.setSilentSender(silent);
    }

    @ParameterizedTest
    @MethodSource("com.kivanval.telegram.bot.UpdatesArguments#provideUpdatesForCanReplyToGreeting")
    void canReplyToGreeting(Update update) {
        assertNotNull(update);
        bot.onUpdateReceived(update);

        Mockito.verify(silent)
                .execute(SendMessage.builder()
                        .chatId(String.valueOf(getChatId(update)))
                        .replyToMessageId(update.getMessage().getMessageId())
                        .text(AbilityConstant.GREETING_REPLY)
                        .build());
    }

    @AfterAll
    static public void tearDown() throws IOException {
        bot.db().close();
    }
}