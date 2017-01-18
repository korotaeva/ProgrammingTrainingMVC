package ru.innopolis.course3.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.hibernate.SubjectEntity;
import ru.innopolis.course3.hibernate.UsersEntity;
import ru.innopolis.course3.pojo.User;

/**
 * Created by korot on 16.01.2017.
 */
@Transactional
@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserDao extends AbstractJpaDAO<UsersEntity> implements IUserDao{

    public UserDao(){
        setClazz(UsersEntity.class );
    }
}