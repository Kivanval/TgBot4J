package com.kivanval.telegram;

import com.kivanval.telegram.bot.TelegramBot;
import com.kivanval.telegram.data.dao.JpaTelegramListDao;
import com.kivanval.telegram.data.dao.JpaTelegramUserDao;
import com.kivanval.telegram.data.dao.TelegramListDao;
import com.kivanval.telegram.data.dao.TelegramUserDao;
import com.kivanval.telegram.data.repositories.JpaTelegramListRepository;
import com.kivanval.telegram.data.repositories.JpaTelegramUserRepository;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.utils.HibernateUtils;
import org.hibernate.Session;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSession();
        JpaTelegramUserDao userDao = new JpaTelegramUserDao(session);
        TelegramUserRepository userRepository = new JpaTelegramUserRepository(userDao);
        JpaTelegramListDao listDao = new JpaTelegramListDao(session);
        TelegramListRepository listRepository = new JpaTelegramListRepository(listDao);

        try {
            // Create the TelegramBotsApi object
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register AbilityBot
            botsApi.registerBot(new TelegramBot(userRepository, listRepository));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


