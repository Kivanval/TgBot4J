package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramUserDao;
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

class JpaTelegramUserRepositoryTest {
    static Session session;
    static TelegramUserRepository repository;

    @BeforeAll
    static void setUp() {
        session = HibernateUtils.getSession();
        JpaTelegramUserDao dao = new JpaTelegramUserDao(session);
        repository = new JpaTelegramUserRepository(dao);
    }

    @ParameterizedTest
    @Disabled
    @MethodSource("com.kivanval.telegram.data.repositories.UserIdArguments#provideUserIdForRepository")
    void add(Long id) {
        TelegramUser user = new TelegramUser();
        user.setId(id);
        user.setFirstName("test");
        user.setIsBot(false);
        user.setUserName("testing" + user.getId());
        repository.add(user);
        Mockito.verify(session).persist(user);
    }

    @Test
    @Disabled
    void update() {
        TelegramUser user = new TelegramUser();
        user.setId(10904L);
        user.setFirstName("test");
        user.setIsBot(false);
        user.setUserName("testing" + user.getId());
        repository.update(user);
        Mockito.verify(session).merge(user);
    }

    @Test
    @Disabled
    void delete() {
        TelegramUser user = new TelegramUser();
        user.setId(10905L);
        user.setFirstName("test");
        user.setIsBot(false);
        repository.delete(user);
        Mockito.verify(session).remove(user);
    }

    @AfterEach
    void tearDown() throws Exception {
    }
}