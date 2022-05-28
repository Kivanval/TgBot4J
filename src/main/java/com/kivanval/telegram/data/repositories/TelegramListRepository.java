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

    List<TelegramList> getByCreatorIdOrderByStartDate(Long creatorId);

    void close();


}
