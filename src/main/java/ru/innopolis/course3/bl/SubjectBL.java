package ru.innopolis.course3.bl;

import org.springframework.stereotype.Service;
import ru.innopolis.course3.pojo.Subject;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.dao.mysql.*;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с темами
 */
@Service
public class SubjectBL implements ISubjectBL {

    DaoFactory factory;
    Connection connection;
    UniversalDao subjectDao;

    public SubjectBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        subjectDao = factory.getDao(connection, Subject.class);
    }

    @Override
    public List<Subject> getAll() throws DataException {
        List<Subject> list = subjectDao.getAll();
        return list;
    }

    @Override
    public List<Subject> getAllByKey(String key, String name) throws DataException {
        List<Subject> list =  (List<Subject>) subjectDao.getByKey(key, name);
        return list;
    }

    @Override
    public Subject create(Subject subject) throws DataException {
        return (Subject)subjectDao.createByObject(subject);
    }

    @Override
    public void delete(Subject subject) throws DataException {
        subjectDao.delete(subject);
    }

    @Override
    public void update(Subject subject) throws DataException {
        subjectDao.update(subject);
    }

    @Override
    public Integer getId(String pk) {
        return null;
    }

    @Override
    public Subject getByPK(Integer id) throws DataException {
        return (Subject)subjectDao.getByPK(id);
    }

    @Override
    public Subject getFromPK(String pk)throws DataException{
        Subject subject = null;
        if(pk != null){
            int id = Integer.parseInt(pk);
            subject = getByPK(id);
        }
        return subject;
    }

}
