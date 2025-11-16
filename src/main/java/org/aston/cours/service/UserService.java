package org.aston.cours.service;

import org.aston.cours.entity.UserEntity;

import java.util.List;

public interface UserService {

    void save(UserEntity user);

    void update(UserEntity user);

    void delete(int id);

    void delete(String email);

    List<UserEntity> getAll();

    UserEntity findById(int id);

    List<UserEntity> findByName(String name);

    UserEntity findByEmail(String email);

    List<UserEntity> findByAge(int age);
}
