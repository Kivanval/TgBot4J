package com.kivanval.telegram.data.utils;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new MetadataSources(
                    new StandardServiceRegistryBuilder()
                            .loadProperties("hibernate.cfg.properties")
                            .build()
            )
                    .addAnnotatedClass(ListedPlaceKey.class)
                    .addAnnotatedClass(ListedPlace.class)
                    .addAnnotatedClass(TelegramList.class)
                    .addAnnotatedClass(TelegramUser.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}
