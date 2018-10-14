package com.heartiger.admin.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class ModelRepository<T extends Serializable, K>{

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;

    private Class<K> idClazz;

    private String idProperty;

    protected String getIdProperty() {
        return idProperty;
    }

    protected void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    protected Class<K> getIdClazz() {
        return idClazz;
    }

    protected void setIdClazz(Class<K> idClazz) {
        this.idClazz = idClazz;
    }

    protected Class<T> getClazz(){
        return this.clazz;
    }

    protected void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    protected T findOne( K id ){
        return entityManager.find( clazz, id );
    }

    protected List<T> findAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    protected T save(T entity){
        entityManager.persist( entity );
        entityManager.flush();
        return entity;
    }

    protected T update(T entity){
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    protected void delete(T entity){
        entityManager.remove(entity);
    }

    protected void deleteById(K entityId) {
        T entity = this.findOne(entityId);
        delete(entity);
    }

    protected void setEntityManager(EntityManager entityManager){
        if(this.entityManager == null)
            this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager(){
        return this.entityManager;
    }
}
