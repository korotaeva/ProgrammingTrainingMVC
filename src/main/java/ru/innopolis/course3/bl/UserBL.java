package ru.innopolis.course3.bl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.ISubjectDao;
import ru.innopolis.course3.dao.IUserDao;
import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;
import ru.innopolis.course3.hibernate.SubjectEntity;
import ru.innopolis.course3.hibernate.UsersEntity;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.pojo.Subject;
import ru.innopolis.course3.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с пользователями
 */
@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserBL  implements IUserBL{



    private IUserDao userDao;

    @Autowired
    public UserBL(IUserDao dao) {
        this.userDao = dao;
        mapperFactory.classMap(UsersEntity.class, User.class)
                .field("username", "name")
                .byDefault()
                .register();

    }

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    private User userfromEntity(UsersEntity userEntity){
        User user = mapper.map(userEntity, User.class);
        return user;
    }

    private UsersEntity userEntityfromUser(User user){
        if(user == null)
            return null;
        UsersEntity userEntity = mapper.map(user, UsersEntity.class);
        return userEntity;
    }

    private List<User> userListfromEntity(Iterable<UsersEntity> listEntity){
        List<User> list = new ArrayList<>();
        for (UsersEntity userEntity:listEntity) list.add(userfromEntity(userEntity));
        return list;
    }



    @Override
    public List<User> getAll() throws DataException {
        List<User> list = userListfromEntity(userDao.findAll());
        return list;
    }

    @Override
    public List<User> getAllByKey(String key, String name) throws DataException {
        List<User> list =  userListfromEntity(userDao.getByKey(key, name));
        return list;
    }
    @Override
    public User getByPK(Integer id) throws DataException {
        return userfromEntity(userDao.findOne(id));
    }

    @Override
    public Integer getIdUser(String name, String password) throws DataException {
        return null;// userDao.getUserId(name, password);
    }

    @Override
    public User create(User user) throws DataException {
        userDao.create(userEntityfromUser(user));
        return user;
    }

    @Override
    public void delete(User object) throws DataException {
        userDao.delete(userEntityfromUser(object));
    }

    @Override
    public void update(User object) throws DataException {
        userDao.update(userEntityfromUser(object));
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
