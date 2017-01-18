package ru.innopolis.course3.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.hibernate.SubjectEntity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by korot on 16.01.2017.
 */
public abstract class AbstractJpaDAO< T extends Serializable> {

    private Class< T > clazz;

    @PersistenceContext
    EntityManager entityManager;

    public final void setClazz( Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T findOne( Integer id ){
        return entityManager.find( clazz, id );
    }
    public List< T > findAll(){
        List<T> entities = entityManager.createQuery( "from " + clazz.getName() ).getResultList();
        return entities;
    }

    public void create( T entity ) {
        entityManager.persist(entity);
    }

    public T update( T entity ){
        return entityManager.merge( entity );
    }


    public void delete( T entity ){
        if(entity != null)
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public void deleteById( Integer entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }

    public  List< T >  getByKey( String key, String name ){
        List< T >  list = entityManager.createQuery( "from " + clazz.getName() + " WHERE " + name + " = " + key).getResultList();
        return list;

    }


}
