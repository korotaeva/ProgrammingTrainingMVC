package ru.innopolis.course3.bl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.dao.ISubjectDao;
import ru.innopolis.course3.hibernate.SubjectEntity;
import ru.innopolis.course3.pojo.Subject;
import ru.innopolis.course3.dao.DataException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с темами
 */
@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class SubjectBL implements ISubjectBL {
    private ISubjectDao dao;

    @Autowired
    public SubjectBL(ISubjectDao dao) {
        this.dao = dao;
    }

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    private Subject subjectfromEntity(SubjectEntity subjectEntity){
        Subject subject = mapper.map(subjectEntity, Subject.class);
        return subject;
    }

    private SubjectEntity subjectEntityfromSubject(Subject subject){
        if(subject == null)
            return null;
        SubjectEntity subjectEntity = mapper.map(subject, SubjectEntity.class);
        return subjectEntity;
    }

    private List<Subject> subjectListfromEntity(Iterable<SubjectEntity> listEntity){
        List<Subject> list = new ArrayList<>();
        for (SubjectEntity subjectEntity:listEntity) list.add(subjectfromEntity(subjectEntity));
        return list;
    }

    @Override
    public List<Subject> getAll() throws DataException {
        Iterable<SubjectEntity> listEntity = dao.findAll();
        return subjectListfromEntity(listEntity);
    }

    @Override
    public List<Subject> getAllByKey(String key, String name) throws DataException {
        Iterable<SubjectEntity> listEntity =  dao.getByKey(key, name);
        return subjectListfromEntity(listEntity);
    }

    @Override
    public Subject create(Subject subject) throws DataException {
        dao.create(subjectEntityfromSubject(subject));
        return subject;
    }

    @Override
    public void delete(Subject subject) throws DataException {
        dao.delete(subjectEntityfromSubject(subject));
    }

    @Override
    public void update(Subject subject) throws DataException {
        dao.update(subjectEntityfromSubject(subject));
    }

    @Override
    public Integer getId(String pk) {
        return null;
    }

    @Override
    public Subject getByPK(Integer id) throws DataException {
        return subjectfromEntity(dao.findOne(id));

    }

    @Override
    public Subject getFromPK(String pk)throws DataException{
        Subject subject = null;
        if(pk != null){
            int id = Integer.parseInt(pk);
            subject = getByPK(id);
        }
        return subject;
    }

}
