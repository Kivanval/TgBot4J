package com.kivanval.telegram.data.repo;

import com.kivanval.telegram.data.dao.JdbcTelegramListDao;
import com.kivanval.telegram.data.dao.JdbcTelegramUserDao;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import jakarta.validation.ValidatorFactory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

import javax.sql.DataSource;
import javax.xml.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcTelegramListRepository implements Repository<TelegramList, Long> {

    private final JdbcTelegramUserDao userDao;
    private final JdbcTelegramListDao listDao;

    @Getter
    @Setter
    public boolean deepExecute;

    public JdbcTelegramListRepository(DataSource dataSource) {
        this.userDao = new JdbcTelegramUserDao(dataSource);
        this.listDao = new JdbcTelegramListDao(dataSource);
    }

    @Override
    public Optional<TelegramList> getById(Long id) {
        return Optional.empty();
    }

    public Set<TelegramList> getByCreatorId(Long id) {
        Set<TelegramList> lists = listDao.readByCreatorId(id);
        if (deepExecute && !lists.isEmpty()) {
            Optional<TelegramUser> userOptional = userDao.readById(id);
            userOptional.ifPresent(telegramUser -> lists.forEach(l -> l.setCreator(telegramUser)));
        }
        return lists;
    }

    public List<TelegramList> getByCreatorIdOrderByStartDate(Long id) {
        return getByCreatorId(id).stream()
                .sorted(Comparator.comparing(TelegramList::getStartDate))
                .toList();
    }

    @Override
    public Collection<TelegramList> getAll() {
        return listDao.readAll();
    }

    @Override
    public int add(TelegramList entity) {
        return listDao.create(entity);
    }

    @Override
    public int addIfAbsent(TelegramList entity) {
        return add(entity);
    }

    @Override
    public int update(TelegramList entity) {
        return 0;
    }

    @Override
    public int remove(TelegramList entity) {
        return 0;
    }
}
