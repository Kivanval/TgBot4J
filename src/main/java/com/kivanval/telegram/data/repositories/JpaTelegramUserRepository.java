package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramUserDao;

public record JpaTelegramUserRepository(JpaTelegramUserDao dao) implements AutoCloseable {




    @Override
    public void close() throws Exception {
        dao.close();
    }
}
