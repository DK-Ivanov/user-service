package org.aston.cours.dao.imp;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.cours.dao.UserDao;
import org.aston.cours.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(UserEntity userEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(userEntity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(UserEntity userEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(userEntity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(userEntity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<UserEntity> findById(int id) {
        return Optional.of(findByQuery("id", id).get(0));
    }

    @Override
    public List<UserEntity> findByName(String name) {
        return findByQuery("name", name);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.of(findByQuery("email", email).get(0));
    }

    @Override
    public List<UserEntity> findByAge(int age) {
        return findByQuery("age", age);
    }

    private <I> List<UserEntity> findByQuery(String fieldName, I value) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
            Root<UserEntity> root = query.from(UserEntity.class);
            query.select(root)
                    .where(criteriaBuilder.equal(root.get(fieldName), value));

            return session.createQuery(query).getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            log.error("Ошибка доступа к данным для поля '{}' со значением '{}'", fieldName, value, e);
            throw new RuntimeException("Ошибка выполнения запроса для поля: " + fieldName, e);
        }
    }

    @Override
    public List<UserEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("FROM UserEntity", UserEntity.class)
                    .getResultList();

        } catch (IllegalArgumentException | PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }

}

