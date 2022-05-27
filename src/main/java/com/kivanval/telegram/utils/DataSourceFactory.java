package com.kivanval.telegram.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public final class DataSourceFactory {

    private DataSourceFactory() {
    }

    public static final String DB_CONFIG = "src/main/resources/database.cfg";


    public static DataSource getPostgresDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        DataSourceConfig cfg = new DataSourceConfig("PG");
        dataSource.setUrl(cfg.url);
        dataSource.setUser(cfg.user);
        dataSource.setPassword(cfg.password);
        return dataSource;
    }

    public static final String URL = "URL";
    public static final String USER = "USER";
    public static final String PASSWORD = "PASSWORD";
    public static final String DB = "DB";
    private static final String DELIM = "_";

    @Data
    @Slf4j
    static final class DataSourceConfig {
        String url;
        String user;
        String password;
        String prefix;

        DataSourceConfig(String prefix) {
            this.prefix = prefix;
            configure(this, prefix);
        }

        static void configure(DataSourceConfig cfg, String prefix) {
            try (FileInputStream fileInputStream = new FileInputStream(DB_CONFIG + ".properties")) {
                final Properties properties = new Properties();
                properties.load(fileInputStream);
                cfg.setUrl(properties.getProperty(String.join(DELIM, prefix, DB, URL)));
                if (cfg.getUrl() == null) log.warn("url for {} is null", prefix);
                cfg.setUser(properties.getProperty(String.join(DELIM, prefix, DB, USER)));
                if (cfg.getUser() == null) log.warn("user for {} is null", prefix);
                cfg.setPassword(properties.getProperty(String.join(DELIM, prefix, DB, PASSWORD)));
                if (cfg.getPassword() == null) log.warn("password for {} is null", prefix);
                log.debug(cfg.toString());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}



