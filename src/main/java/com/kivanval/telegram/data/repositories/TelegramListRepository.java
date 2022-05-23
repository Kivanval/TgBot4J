package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramListDao;
import com.kivanval.telegram.models.TelegramList;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface TelegramListRepository extends Repository<TelegramList, Long> {
    Optional<TelegramList> getByAlias(String alias);

    List<TelegramList> getByManagerId(Long managerId);

    List<TelegramList> getByUserId(Long userId);

    List<TelegramList> getByCreatorId(Long creatorId);

    List<TelegramList> getExistingByCreatorId(Long creatorId);

    List<TelegramList> getExistingByCreatorIdOrderByStartDate(Long creatorId);

    static JpaTelegramListRepository jpaInstance(EntityManager em) {
        return new JpaTelegramListRepository(new JpaTelegramListDao(em));
    }

    void close();


}
