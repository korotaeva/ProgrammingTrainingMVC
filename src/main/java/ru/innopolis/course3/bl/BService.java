package ru.innopolis.course3.bl;

import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.dao.Identified;

import java.io.Serializable;
import java.util.List;

/**
 * Created by korot on 10.01.2017.
 */
public interface BService <T extends Identified<PK>, PK extends Serializable> {

    public List<T> getAll() throws DataException;

    public List<T> getAllByKey(String key, String name)throws DataException;

    public T getByPK(PK id)throws DataException;

    public T create(T object)throws DataException;

    public void delete(T object) throws DataException;

    public void update(T object) throws DataException;

    public T getFromPK(String pk) throws DataException;

    public PK getId(String pk);
}
