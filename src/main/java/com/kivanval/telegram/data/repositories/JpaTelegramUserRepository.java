package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramUserDao;
import com.kivanval.telegram.models.TelegramUser;

import java.util.List;
import java.util.Optional;

public record JpaTelegramUserRepository(JpaTelegramUserDao dao) implements TelegramUserRepository {

    @Override
    public List<TelegramUser> getAll() {
        return dao.readAll();
    }

    @Override
    public Optional<TelegramUser> getById(Long id) {
        return dao.readById(id);
    }

    @Override
    public Optional<TelegramUser> getByUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public List<TelegramUser> getManagersByListId(Long listId) {
        return dao.readManagersByListId(listId);
    }

    @Override
    public Optional<TelegramUser> getCreatorByListId(Long listId) {
        return dao.readCreatorByListId(listId);
    }

    @Override
    public List<TelegramUser> getUsersByListId(Long listId) {
        return dao.readUsersByListId(listId);
    }

    @Override
    public void add(TelegramUser entity) {
        dao.create(entity);
    }

    @Override
    public void update(TelegramUser entity) {
        dao.update(entity);
    }

    @Override
    public void delete(TelegramUser entity) {
        dao.remove(entity);
    }


    @Override
    public void close() {
        dao.close();
    }

}
