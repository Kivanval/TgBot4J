package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record JpaTelegramUserDao(EntityManager entityManager) implements TelegramUserDao {

    @Override
    public Optional<TelegramUser> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TelegramUser.class, id));
    }

    @Override
    public List<TelegramUser> readAll() {
        TypedQuery<TelegramUser> query = entityManager.createQuery("""
                SELECT u
                    FROM TelegramUser u
                """, TelegramUser.class);
        return query.getResultList();
    }

    @Override
    public Optional<TelegramUser> readByUserName(String userName) {
        TypedQuery<TelegramUser> query = entityManager.createQuery("""
                        SELECT u
                            FROM TelegramUser u
                            WHERE u.userName = :userName
                        """, TelegramUser.class)
                .setParameter("userName", userName);
        List<TelegramUser> list = query.getResultList();
        return list.stream().findAny();
    }

    @Override
    public List<TelegramUser> readManagersByListId(Long listId) {
        TypedQuery<TelegramUser> query = entityManager.createQuery("""
                        SELECT m
                            FROM TelegramList l
                            JOIN l.managers m
                            WHERE l.id = :listId
                        """, TelegramUser.class)
                .setParameter("listId", listId);
        return query.getResultList();
    }

    @Override
    public Optional<TelegramUser> readCreatorByListId(Long listId) {
        TypedQuery<TelegramUser> query = entityManager.createQuery("""
                        SELECT l.creator
                            FROM TelegramList l
                            WHERE l.id = :listId
                        """, TelegramUser.class)
                .setParameter("listId", listId);
        List<TelegramUser> list = query.getResultList();
        return list.stream().findAny();
    }

    @Override
    public List<TelegramUser> readUsersByListId(Long listId) {
        TypedQuery<TelegramUser> query = entityManager.createQuery("""
                        SELECT lp.user
                            FROM ListedPlace lp
                            WHERE lp.list.id = :listId
                        """, TelegramUser.class)
                .setParameter("listId", listId);
        return query.getResultList();
    }

    @Override
    public void create(TelegramUser entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    @Override
    public void update(TelegramUser entity) {
        executeInsideTransaction(em -> em.merge(entity));
    }

    @Override
    public void remove(TelegramUser entity) {
        executeInsideTransaction(em -> em.remove(entity));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void close() {
        entityManager.close();
    }
}
