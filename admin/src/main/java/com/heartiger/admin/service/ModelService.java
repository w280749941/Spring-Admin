package com.heartiger.admin.service;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface ModelService<T extends Serializable, K> {

    T findOne(final K id);

    List<T> findAll();

    T create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final K entityId);

    void setClass( Class<T> clazzToSet );

    void setEntityManager(EntityManager entityManager);

    Class<K> getIdClazz();

    void setIdClazz(Class<K> idClazz);
}