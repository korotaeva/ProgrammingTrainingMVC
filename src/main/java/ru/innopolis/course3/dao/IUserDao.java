package ru.innopolis.course3.dao;

import ru.innopolis.course3.hibernate.UsersEntity;

import java.util.List;

/**
 * Created by korot on 18.01.2017.
 */
public interface IUserDao {

    UsersEntity findOne(Integer id);

    List<UsersEntity> findAll();

    void create(UsersEntity entity);

    UsersEntity update(UsersEntity entity);

    void delete(UsersEntity entity);

    void deleteById(Integer entityId);

    List<UsersEntity>  getByKey( String key, String name);

}