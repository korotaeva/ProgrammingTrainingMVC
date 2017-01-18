package ru.innopolis.course3.dao;

import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;
import ru.innopolis.course3.hibernate.SubjectEntity;

import java.util.List;

/**
 * Created by korot on 16.01.2017.
 */

public interface ISubjectDao {

    SubjectEntity findOne(Integer id);

    List<SubjectEntity> findAll();

    void create(SubjectEntity entity);

    SubjectEntity update(SubjectEntity entity);

    void delete(SubjectEntity entity);

    void deleteById(Integer entityId);

    List<SubjectEntity>  getByKey( String key, String name);

}