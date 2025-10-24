package org.aston.cours.dao.imp;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.cours.dao.UserDao;
import org.aston.cours.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.of(findByQuery("id", id).get(0));
    }

    @Override
    public List<User> findByName(String name) {
        return findByQuery("name", name);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.of(findByQuery("email", email).get(0));
    }

    @Override
    public List<User> findByAge(int age) {
        return findByQuery("age", age);
    }

    private <I> List<User> findByQuery(String fieldName, I value) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(criteriaBuilder.equal(root.get(fieldName), value));

            return session.createQuery(query).getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            log.error("Ошибка доступа к данным для поля '{}' со значением '{}'", fieldName, value, e);
            throw new RuntimeException("Ошибка выполнения запроса для поля: " + fieldName, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("FROM User", User.class)
                    .getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }

}

