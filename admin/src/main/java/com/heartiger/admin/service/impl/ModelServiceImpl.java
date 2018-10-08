package com.heartiger.admin.service.impl;

import com.heartiger.admin.repository.ModelRepository;
import com.heartiger.admin.service.ModelService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Service
public class ModelServiceImpl<T extends Serializable, K> extends ModelRepository<T, K> implements ModelService<T, K> {

    @Override
    public T findOne(K id) {
        return super.findOne(id);
    }

    @Override
    @Transactional
    public T create(T entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return super.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(K entityId) {
        super.deleteById(entityId);
    }

    @Override
    public List<T> findAll(){
        return super.findAll();
    }

    @Override
    public Class<T> getClazz(){
        return super.getClazz();
    }

    @Override
    public void setClass(Class<T> clazzToSet) {
        super.setClazz(clazzToSet);
    }

    @Override
    public Class<K> getIdClazz(){
        return super.getIdClazz();
    }

    @Override
    public void setIdClazz(Class<K> idClazz){
        super.setIdClazz(idClazz);
    }
}