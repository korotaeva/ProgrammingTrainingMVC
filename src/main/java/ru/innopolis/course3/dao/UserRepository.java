package ru.innopolis.course3.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.course3.hibernate.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

//    @Query("select g from Goal g where g.name = :name")
//    Goal findByName(@Param("name") String name);
}