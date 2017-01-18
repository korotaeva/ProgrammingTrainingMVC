package ru.innopolis.course3.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;

/**
 * Created by korot on 16.01.2017.
 */

@Transactional
@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class PracticalDao extends AbstractJpaDAO<PracticalAssignmentsEntity> implements IPracticalDao{

    public PracticalDao(){
        setClazz(PracticalAssignmentsEntity.class );
    }
}