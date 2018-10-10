package com.heartiger.admin.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface ModelService<T extends Serializable, K> {

    T findOne(final K id);

    List<T> findAll();

    T create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final K entityId);

    Class<T> getClazz();

    void setClass( Class<T> clazzToSet );

    void setEntityManager(EntityManager entityManager);

    Class<K> getIdClazz();

    void setIdClazz(Class<K> idClazz);

    void setIdProperty(String idProperty);

    String getIdProperty();
}