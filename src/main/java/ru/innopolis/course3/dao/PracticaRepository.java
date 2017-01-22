package ru.innopolis.course3.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;

import java.util.List;

@Repository
public interface PracticaRepository extends JpaRepository<PracticalAssignmentsEntity, Integer> {

    List<PracticalAssignmentsEntity> findBySubject(Integer subject);
}