package ru.innopolis.course3.bl;

import org.springframework.stereotype.Service;
import ru.innopolis.course3.pojo.User;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.dao.mysql.*;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с пользователями
 */
@Service
public class UserBL  implements IUserBL{

    DaoFactory factory;
    Connection connection;
    UniversalDao userDao;

    public UserBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        userDao = factory.getDao(connection, User.class);
    }

    public UserBL(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }
    @Override
    public List<User> getAll() throws DataException {
        List<User> list = userDao.getAll();
        return list;
    }

    @Override
    public List<User> getAllByKey(String key, String name) throws DataException {
        List<User> list =  (List<User>) userDao.getByKey(key, name);
        return list;
    }
    @Override
    public User getByPK(Integer id) throws DataException {
        return (User)userDao.getByPK(id);
    }

    @Override
    public Integer getIdUser(String name, String password) throws DataException {
        return MySqlUserDao.getUserId(name, password);
    }

    @Override
    public User create(User user) throws DataException {
        return (User)userDao.createByObject(user);
    }

    @Override
    public void delete(User object) throws DataException {
        userDao.delete(object);
    }

    @Override
    public void update(User object) throws DataException {
        userDao.update(object);
    }

    @Override
    public User getFromPK(String pk) throws DataException {
        User user = null;
        if(pk != null && !pk.equals("")){
            int id = Integer.parseInt(pk);
            user = getByPK(id);
        }
        return user;
    }

    @Override
    public Integer getId(String pk) {
        Integer id = null;
        if (pk != null && !pk.equals("")) {
            id = Integer.parseInt(pk);
        }
        return id;
    }





}
