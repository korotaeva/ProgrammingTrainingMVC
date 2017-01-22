package ru.innopolis.course3.bl;

import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.pojo.User;

import java.util.List;

/**
 * Created by korot on 11.01.2017.
 */
public interface IUserBL extends BService <User, Integer>{
    Integer getIdUser(String name, String password) throws DataException;


}
