package com.kivanval.telegram;

import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.data.dao.HibernateTelegramListDao;
import com.kivanval.telegram.utils.HibernateUtils;
import org.hibernate.Session;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {

    public static void main(String[] args) throws Exception {
        Session session = HibernateUtils.getSession();
        try (HibernateTelegramListDao htld = new HibernateTelegramListDao(session)) {
            System.out.println(htld.readAll());
        }
        /*try {
            // Create the TelegramBotsApi object
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register AbilityBot
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }
}


