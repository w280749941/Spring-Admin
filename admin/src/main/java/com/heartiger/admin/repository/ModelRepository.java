package com.heartiger.admin.repository;

import com.heartiger.admin.service.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class ModelRepository<T extends Serializable, K>{

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;

    private Class<K> idClazz;

    public Class<K> getIdClazz() {
        return idClazz;
    }

    public void setIdClazz(Class<K> idClazz) {
        this.idClazz = idClazz;
    }

    public void setClazz(Class<T> clazzToSet ) {
        this.clazz = clazzToSet;
    }

    public T findOne( K id ){
        return entityManager.find( clazz, id );
    }

    public List<T> findAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    public T save(T entity ){
        entityManager.persist( entity );
        entityManager.flush();
        return entity;
    }

    public T update(T entity){
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }

    public void deleteById( K entityId ) {
        T entity = this.findOne(entityId);
        delete(entity);
    }

    public void setEntityManager(EntityManager entityManager){
        if(this.entityManager == null)
            this.entityManager = entityManager;
    }
}
