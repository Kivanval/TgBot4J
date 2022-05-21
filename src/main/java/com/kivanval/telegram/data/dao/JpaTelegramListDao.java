package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record JpaTelegramListDao(EntityManager entityManager) implements TelegramListDao {

    @Override
    public Optional<TelegramList> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TelegramList.class, id));
    }

    @Override
    public Optional<TelegramList> readByAlias(String alias) {
        TypedQuery<TelegramList> query = entityManager.createQuery("""
                        SELECT l
                            FROM TelegramList l
                            WHERE l.alias = :alias
                        """, TelegramList.class)
                .setParameter("alias", alias);
        List<TelegramList> list = query.getResultList();
        return list.stream().findAny();
    }

    @Override
    public List<TelegramList> readByManagerId(Long managerId) {
        TypedQuery<TelegramList> query = entityManager.createQuery("""
                        SELECT l
                            FROM TelegramList l
                            JOIN l.managers m
                            WHERE m.id = :managerId
                        """, TelegramList.class)
                .setParameter("managerId", managerId);
        return query.getResultList();
    }

    @Override
    public List<TelegramList> readByUserId(Long userId) {
        TypedQuery<TelegramList> query = entityManager.createQuery("""
                        SELECT lp.list
                            FROM ListedPlace lp
                            WHERE lp.user.id = :userId
                        """, TelegramList.class)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<TelegramList> readByCreatorId(Long creatorId) {
        TypedQuery<TelegramList> query = entityManager.createQuery("""
                        SELECT l
                            FROM TelegramList l
                            WHERE l.creator.id = :creatorId
                            ORDER BY l.startDate
                        """, TelegramList.class)
                .setParameter("creatorId", creatorId);
        return query.getResultList();
    }

    @Override
    public List<TelegramList> readAll() {
        TypedQuery<TelegramList> query = entityManager.createQuery("""
                SELECT l
                    FROM TelegramList l
                """, TelegramList.class);
        return query.getResultList();
    }

    @Override
    public void create(TelegramList entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    @Override
    public void update(TelegramList entity) {
        executeInsideTransaction(em -> em.merge(entity));
    }

    @Override
    public void remove(TelegramList entity) {
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
