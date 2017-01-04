package ru.innopolis.course3.bl;

import ru.innopolis.course3.pojo.Subject;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с темами
 */
public class SubjectBL {

    DaoFactory factory;
    Connection connection;
    UniversalDao subjectDao;

    public SubjectBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        subjectDao = factory.getDao(connection, Subject.class);
    }

    public List<Subject> getAll() throws DataException {
        List<Subject> list = subjectDao.getAll();
        return list;
    }

    public Subject create(Subject subject) throws DataException {
        return (Subject)subjectDao.createByObject(subject);
    }

    public void delete(Subject subject) throws DataException {
        subjectDao.delete(subject);
    }

    public void update(Subject subject) throws DataException {
        subjectDao.update(subject);
    }

    public Subject getByPK(Integer id) throws DataException {
        return (Subject)subjectDao.getByPK(id);
    }

    public Subject subjectFromPK(String pk)throws DataException{
        Subject subject = null;
        if(pk != null){
            int id = Integer.parseInt(pk);
            subject = getByPK(id);
        }
        return subject;
    }

}
