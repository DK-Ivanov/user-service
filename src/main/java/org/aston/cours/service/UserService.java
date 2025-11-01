package org.aston.cours.service;

import org.aston.cours.model.UserEntity;

import java.util.List;

public interface UserService {

    public void create(UserEntity userEntity);
    public void update(UserEntity userEntity);
    public void delete(int id);
    public List<UserEntity> getAll();
    public UserEntity findById(int id);
    public List<UserEntity> findByName(String name);
    public UserEntity findByEmail(String email);
    public List<UserEntity> findByAge(int age);
}
