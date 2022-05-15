package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.HibernateTelegramUserDao;

public record JpaTelegramUserRepository(HibernateTelegramUserDao dao) implements AutoCloseable {




    @Override
    public void close() throws Exception {
        dao.close();
    }
}
