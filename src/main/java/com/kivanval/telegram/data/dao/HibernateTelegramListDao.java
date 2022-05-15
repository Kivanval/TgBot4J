package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramList_;
import com.kivanval.telegram.models.TelegramUser;
import com.kivanval.telegram.models.TelegramUser_;
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

    public Optional<TelegramList> readByAccessKey(String accessKey) {
        return session.byNaturalId(TelegramList.class)
                .using("accessKey", accessKey)
                .loadOptional();
    }


    public Optional<TelegramList> readByAlias(String alias) {
        return session.byNaturalId(TelegramList.class)
                .using("alias", alias)
                .loadOptional();
    }

    public List<TelegramList> readByManagerId(Long managerId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TelegramList> cq = cb.createQuery(TelegramList.class);
        Root<TelegramList> root = cq.from(TelegramList.class);
        Join<TelegramList, TelegramUser> join = root.join(TelegramList_.managers);
        Predicate p = cb.equal(join.get(TelegramUser_.id), managerId);
        cq.where(p);
        cq.select(root);
        TypedQuery<TelegramList> query = session.createQuery(cq);
        return query.getResultList();
    }

    public List<TelegramList> readByAdminId(Long adminId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TelegramList> cq = cb.createQuery(TelegramList.class);
        Root<TelegramList> root = cq.from(TelegramList.class);
        Join<TelegramList, TelegramUser> join = root.join(TelegramList_.admin);
        Predicate p = cb.equal(join.get(TelegramUser_.id), adminId);
        cq.where(p);
        cq.select(root);
        TypedQuery<TelegramList> query = session.createQuery(cq);
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
