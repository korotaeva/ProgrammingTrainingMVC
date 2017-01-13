package ru.innopolis.course3.dao.mysql;

import ru.innopolis.course3.dao.AbstractJDBCDao;
import ru.innopolis.course3.dao.UniversalDao;
import ru.innopolis.course3.pojo.User;

/**
 * Created by korot on 12.01.2017.
 */
public interface IMySqlUserDao  extends UniversalDao<User, Integer> {
}
