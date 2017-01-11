package ru.innopolis.course3.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.dao.DaoFactory;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.dao.mysql.*;

import java.sql.Connection;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с практическими заданиями
 */
@Service
public class PracticalAssignmentsBL implements IPracticalAssignmentsBL {
    DaoFactory factory;
    Connection connection;
    UniversalDao practicalDao;

    public PracticalAssignmentsBL() throws DataException {
        factory = new MySqlDaoFactory();
        connection = (Connection) factory.getContext();
        practicalDao = factory.getDao(connection, PracticalAssignments.class);

    }

    @Override
    public List<PracticalAssignments> getAll() throws DataException {
        List<PracticalAssignments> list = practicalDao.getAll();
        return list;
    }

    @Override
    public List<PracticalAssignments> getAllByKey(String key, String name) throws DataException {
        List<PracticalAssignments> list =  (List<PracticalAssignments>) practicalDao.getByKey(key, name);
        return list;
    }

    @Override
    public PracticalAssignments getByPK(Integer id) throws DataException {
        return (PracticalAssignments)practicalDao.getByPK(id);
    }
    @Override
    public PracticalAssignments create(PracticalAssignments practicalAssignments) throws DataException {
        return (PracticalAssignments)practicalDao.createByObject(practicalAssignments);
    }
    @Override
    public void delete(PracticalAssignments subject) throws DataException {
        practicalDao.delete(subject);
    }
    @Override
    public void update(PracticalAssignments subject) throws DataException {
        practicalDao.update(subject);
    }

    @Override
    public PracticalAssignments getFromPK(String pk) throws DataException {
        PracticalAssignments practicalAssignments = null;
        if(pk != null && !pk.equals("")){
            int id = Integer.parseInt(pk);
            practicalAssignments = getByPK(id);
        }
        return practicalAssignments;
    }


    @Override
    public Integer getId(String pk){
        Integer idSubject = null;
        if (pk != null && !pk.equals("")) {
            idSubject = Integer.parseInt(pk);
        }
        return idSubject;
    }
}
