package ru.innopolis.course3.dao;

import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;
import ru.innopolis.course3.hibernate.SubjectEntity;

import java.util.List;

/**
 * Created by korot on 16.01.2017.
 */

public interface IPracticalDao {

    PracticalAssignmentsEntity findOne(Integer id);

    List<PracticalAssignmentsEntity> findAll();

    void create(PracticalAssignmentsEntity entity);

    PracticalAssignmentsEntity update(PracticalAssignmentsEntity entity);

    void delete(PracticalAssignmentsEntity entity);

    void deleteById(Integer entityId);

    List<PracticalAssignmentsEntity>  getByKey(String key, String name);
}