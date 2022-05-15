package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramUser;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record HibernateTelegramUserDao(Session session) implements Dao<TelegramUser, Long> {

    @Override
    public Optional<TelegramUser> readById(Long id) {
        return Optional.ofNullable(session.find(TelegramUser.class, id));
    }

    @Override
    public List<TelegramUser> readAll() {
        TypedQuery<TelegramUser> query = session.createQuery("SELECT u FROM TelegramUser u", TelegramUser.class);
        return query.getResultList();
    }

    @Override
    public void create(TelegramUser entity) {
        executeInsideTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(TelegramUser entity) {
        executeInsideTransaction(session -> session.merge(entity));
    }

    @Override
    public void delete(TelegramUser entity) {
        executeInsideTransaction(session -> session.remove(entity));
    }

    private void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
