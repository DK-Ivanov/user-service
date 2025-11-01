package org.aston.cours;

import org.aston.cours.controller.UserConsoleController;
import org.aston.cours.dao.UserDao;
import org.aston.cours.dao.imp.UserDaoImpl;
import org.aston.cours.model.UserEntity;
import org.aston.cours.service.imp.UserServiceImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Главный класс программы.
 * */
public class Runner {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .build();
        SessionFactory sessionFactory =
                new MetadataSources(registry)
                        .addAnnotatedClass(UserEntity.class)
                        .buildMetadata()
                        .buildSessionFactory();

        UserDao userDao = new UserDaoImpl(sessionFactory);
        UserServiceImpl service = new UserServiceImpl(userDao);
        UserConsoleController logic = new UserConsoleController(service);
        logic.start();
    }
}