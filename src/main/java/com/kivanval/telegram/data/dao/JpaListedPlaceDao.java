package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record JpaListedPlaceDao(EntityManager entityManager) implements ListedPlaceDao {

    @Override
    public Optional<ListedPlace> readById(ListedPlaceKey id) {
        return Optional.ofNullable(entityManager.find(ListedPlace.class, id));
    }

    @Override
    public List<ListedPlace> readAll() {
        TypedQuery<ListedPlace> query = entityManager.createQuery("""
                SELECT p
                    FROM ListedPlace p
                """, ListedPlace.class);
        return query.getResultList();
    }

    public List<ListedPlace> readByUserId(Long userId) {
        TypedQuery<ListedPlace> query = entityManager.createQuery("""
                        SELECT p
                            FROM ListedPlace p
                            WHERE p.user.id = :userId
                        """, ListedPlace.class)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    public List<ListedPlace> readByListId(Long listId) {
        TypedQuery<ListedPlace> query = entityManager.createQuery("""
                        SELECT p
                            FROM ListedPlace p
                            WHERE p.list.id = :listId
                        """, ListedPlace.class)
                .setParameter("listId", listId);
        return query.getResultList();
    }

    @Override
    public void create(ListedPlace entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    @Override
    public void update(ListedPlace entity) {
        executeInsideTransaction(em -> em.merge(entity));
    }

    @Override
    public void remove(ListedPlace entity) {
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
