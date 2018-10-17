package com.heartiger.admin.container;

import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.service.impl.ModelServiceImpl;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Component
public class AdminContainer {

    private Map<String, ModelService> hm;

    public AdminContainer(){
        this.hm = new HashMap<>();
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz, Class<K> idClazz, String idProperty){

        ModelService<T, K> modelService = new ModelServiceImpl<>();
        modelService.setClass(clazz);
        modelService.setIdClazz(idClazz);
        modelService.setIdProperty(idProperty);
        hm.put(name, modelService);
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz) throws Exception {

        ModelService<T, K> modelService = new ModelServiceImpl<>();
        for(Field field : clazz.getDeclaredFields()){
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations)
            {
                if(annotation.annotationType().equals(Id.class))
                {
                    modelService.setIdProperty(field.getName());
                    modelService.setIdClazz((Class<K>) field.getType());
                    modelService.setClass(clazz);
                    hm.put(name, modelService);
                    return;
                }
            }
        }
        throw new Exception("Can't find any field with ID annotation in " + clazz.getName());
    }

    public <T extends Serializable, K> ModelService<T, K> getService(String name){
        if(this.hm.containsKey(name))
            return this.hm.get(name);
        return null;
    }

    public void clearContainer(){
        this.hm.clear();
    }
}
