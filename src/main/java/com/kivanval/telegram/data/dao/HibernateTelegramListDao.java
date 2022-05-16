package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record HibernateTelegramListDao(Session session) implements Dao<TelegramList, String> {

    @Override
    public Optional<TelegramList> readById(String id) {
        return session.byId(TelegramList.class)
                .loadOptional(id);
    }


    public Optional<TelegramList> readByAlias(String alias) {
        return session.bySimpleNaturalId(TelegramList.class)
                .loadOptional(alias);
    }

    public List<TelegramList> readByManagerId(Long managerId) {
        TypedQuery<TelegramList> query = session.createQuery("""
                        SELECT l
                            FROM TelegramList l
                            JOIN l.managers m
                            WHERE m.id = :managerId
                        """, TelegramList.class)
                .setParameter("managerId", managerId);
        return query.getResultList();
    }

    public List<TelegramList> readByAdminId(Long creatorId) {
        TypedQuery<TelegramList> query = session.createQuery("""
                        SELECT l
                            FROM TelegramList l
                            WHERE l.creator.id = :creatorId
                        """, TelegramList.class)
                .setParameter("creatorId", creatorId);
        return query.getResultList();
    }

    @Override
    public List<TelegramList> readAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TelegramList> cq = cb.createQuery(TelegramList.class);
        Root<TelegramList> root = cq.from(TelegramList.class);
        cq.select(root);
        TypedQuery<TelegramList> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void create(TelegramList entity) {
        executeInsideTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(TelegramList entity) {
        executeInsideTransaction(session -> session.merge(entity));
    }

    @Override
    public void delete(TelegramList entity) {
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
