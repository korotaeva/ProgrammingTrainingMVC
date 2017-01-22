package ru.innopolis.course3.bl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.dao.PracticaRepository;
import ru.innopolis.course3.hibernate.PracticalAssignmentsEntity;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.dao.DataException;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * Бизнес сервер для работы с практическими заданиями
 */
@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@EnableJpaRepositories("ru.innopolis.course3.dao")
public class PracticalAssignmentsBL implements IPracticalAssignmentsBL {

   MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

   MapperFacade mapper = mapperFactory.getMapperFacade();

    private PracticalAssignments practicalAssignmentsFromEntity(PracticalAssignmentsEntity practicalAssignmentsEntity){

        PracticalAssignments practicalAssignments = mapper.map(practicalAssignmentsEntity, PracticalAssignments.class);
        return practicalAssignments;
    }

    private PracticalAssignmentsEntity practicalEntityfromPractical(PracticalAssignments practicalAssignments){
        if(practicalAssignments == null)
            return null;
        PracticalAssignmentsEntity practicalAssignmentsEntity = mapper.map(practicalAssignments, PracticalAssignmentsEntity.class);
        return practicalAssignmentsEntity;
    }

    private List<PracticalAssignments> practicalListfromEntity(Iterable<PracticalAssignmentsEntity> listEntity){
        List<PracticalAssignments> list = new ArrayList<>();
        for (PracticalAssignmentsEntity subjectEntity:listEntity) list.add(practicalAssignmentsFromEntity(subjectEntity));
        return list;
    }



    private PracticaRepository practicalDao;

    @Autowired
    public PracticalAssignmentsBL(PracticaRepository dao) {
        this.practicalDao = dao;
        mapperFactory.classMap(PracticalAssignmentsEntity.class, PracticalAssignments.class)
                .field("subject", "subject.id")
                .byDefault()
                .register();
    }

    @Override
    public List<PracticalAssignments> getAll() throws DataException {
        List<PracticalAssignments> list = practicalListfromEntity(practicalDao.findAll());
        return list;
    }

    @Override
    public List<PracticalAssignments> getAllByKey(String key, String name) throws DataException {
        List<PracticalAssignments> list =  practicalListfromEntity(practicalDao.findBySubject(Integer.parseInt(key)));
        return list;
    }

    @Override
    public PracticalAssignments getByPK(Integer id) throws DataException {
        return practicalAssignmentsFromEntity(practicalDao.findOne(id));
    }
    @Override
    public PracticalAssignments create(PracticalAssignments practicalAssignments) throws DataException {
        practicalDao.save(practicalEntityfromPractical(practicalAssignments));
        return practicalAssignments;
    }
    @Override
    public void delete(PracticalAssignments practicalAssignments) throws DataException {
        practicalDao.delete(practicalEntityfromPractical(practicalAssignments));
    }
    @Override
    public void update(PracticalAssignments practicalAssignments) throws DataException {
        practicalDao.save(practicalEntityfromPractical(practicalAssignments));
    }

    @Override
    public PracticalAssignments getFromPK(String pk) throws DataException {
        PracticalAssignments practicalAssignments = null;
        if(pk != null && !pk.equals("")){
            int id = Integer.parseInt(pk);
            practicalAssignments = getByPK(id);
        }
        return practicalAssignments;
    }


    @Override
    public Integer getId(String pk){
        Integer idSubject = null;
        if (pk != null && !pk.equals("")) {
            idSubject = Integer.parseInt(pk);
        }
        return idSubject;
    }
}
