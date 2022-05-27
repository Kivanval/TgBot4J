package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


@Slf4j
public class JdbcTelegramListDao implements JdbcDao<TelegramList, Long> {

    DataSource dataSource;

    public JdbcTelegramListDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<TelegramList> parse(ResultSet resultSet) {
        Set<TelegramList> lists = new TreeSet<>(Comparator.comparing(TelegramList::getId));
        try {
            while (resultSet.next()) {
                TelegramList list = new TelegramList();
                list.setId(resultSet.getLong("id"));
                TelegramUser user = new TelegramUser();
                list.setCreator(user);
                user.setId(resultSet.getLong("creator_id"));
                list.setTitle(resultSet.getString("title"));
                list.setStartDate(resultSet.getTimestamp("start_date"));
                list.setEndDate(resultSet.getTimestamp("end_date"));
                list.setFreeze(resultSet.getBoolean("is_freeze"));
                lists.add(list);
            }
            return lists;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Collections.emptySet();
    }

    @Override
    public Optional<TelegramList> readById(Long id) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    SELECT
                        l.id,
                        l.title,
                        l.start_date,
                        l.end_date,
                        l.max_size,
                        l.is_freeze
                    FROM
                        lists l
                    WHERE
                        l.id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            Set<TelegramList> lists = parse(resultSet);
            return lists.stream().findFirst();

        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Set<TelegramList> readAll() {
        Statement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    SELECT
                        l.id,
                        l.creator_id,
                        l.title,
                        l.start_date,
                        l.end_date,
                        l.max_size,
                        l.is_freeze
                    FROM
                        lists l
                    """;
            log.info(query);
            statement = conn.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return parse(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return Collections.emptySet();
    }

    public Set<TelegramList> readByCreatorId(Long creatorId) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    SELECT
                        l.id,
                        l.creator_id,
                        l.title,
                        l.start_date,
                        l.end_date,
                        l.max_size,
                        l.is_freeze
                    FROM
                        lists l
                    WHERE l.creator_id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, creatorId);
            ResultSet resultSet = statement.executeQuery();
            return parse(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return Collections.emptySet();
    }


    @Override
    public int create(TelegramList entity) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    INSERT INTO lists (creator_id, title, start_date, end_date, max_size, is_freeze)
                        VALUES (?, ?, ?, ?, ?, ?)
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, entity.getCreator().getId());
            statement.setString(2, entity.getTitle());
            statement.setTimestamp(3, entity.getStartDate());
            statement.setTimestamp(4, entity.getEndDate());
            statement.setInt(5, entity.getMaxSize());
            statement.setBoolean(6, entity.isFreeze());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }

    @Override
    public int update(TelegramList entity) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    UPDATE lists
                        SET creator_id = ?,
                            title = ?,
                            max_size = ?,
                            start_date = ?,
                            end_date = ?,
                            is_freeze = ?
                    WHERE id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, entity.getCreator().getId());
            statement.setString(2, entity.getTitle());
            statement.setTimestamp(3, entity.getStartDate());
            statement.setTimestamp(4, entity.getEndDate());
            statement.setInt(5, entity.getMaxSize());
            statement.setLong(6, entity.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }

    @Override
    public int upsert(TelegramList entity) {
        return create(entity);
    }

    @Override
    public int removeById(Long id) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    DELETE FROM lists
                    WHERE id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }

}
