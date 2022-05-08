package com.kivanval.telegram;

import com.kivanval.telegram.bots.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {
    public static void main(String[] args) {

        try {
            // Create the TelegramBotsApi object
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register AbilityBot
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

