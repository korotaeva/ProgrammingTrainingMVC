package ru.innopolis.course3.dao;



//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.course3.hibernate.SubjectEntity;

import java.util.List;

@Repository
public interface SubjectEntityRepository extends JpaRepository<SubjectEntity, Integer> {

   /* @Query("from SubjectEntity  WHERE :key = :name")
    public List<SubjectEntity> getByKey(@Param("key") String key, @Param("name") String name);
*/
}