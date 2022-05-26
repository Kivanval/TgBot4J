package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramListDao;
import com.kivanval.telegram.data.dao.JpaTelegramUserDao;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import com.kivanval.telegram.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

class JpaTelegramListRepositoryTest {
    static Session session;
    static TelegramListRepository repository;

    @BeforeAll
    static void setUp() {
        session = HibernateUtils.getSession();
        JpaTelegramListDao dao = new JpaTelegramListDao(session);
        repository = new JpaTelegramListRepository(dao);
    }

    @Test
    void add() {
        TelegramUserRepository userRepository = new JpaTelegramUserRepository(new JpaTelegramUserDao(session));
        TelegramList list = new TelegramList();
        list.setState(TelegramList.State.FREEZE);
        TelegramUser user = userRepository.getById(3L).orElseThrow();
        userRepository.update(user);
        list.setCreator(user);
        list.setStartDate(LocalDateTime.now());
        repository.add(list);
    }

    @Test
    @Disabled
    void update() {
        TelegramUserRepository userRepository = new JpaTelegramUserRepository(new JpaTelegramUserDao(session));
        TelegramUser user = userRepository.getById(3L).orElseThrow();
        user.setLastName("test");
        TelegramList list = new TelegramList();
        list.setState(TelegramList.State.ACTIVE);
        list.setStartDate(LocalDateTime.now());
        list.setCreator(user);
        repository.update(list);
    }

    @Test
    @Disabled
    void delete() {
        repository.delete(repository.getById(10L).orElseThrow());
    }

    @AfterEach
    void tearDown() {
    }
}