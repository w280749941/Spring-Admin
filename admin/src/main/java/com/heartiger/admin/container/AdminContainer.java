package com.heartiger.admin.container;

import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.service.impl.ModelServiceImpl;
import com.heartiger.admin.utils.TypeConverter;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


@Component
public class AdminContainer {

    private Map<String, ModelService> hm;
    private Map<Object, List<Map<Object, Object>>> propertyContainer;


    public AdminContainer(){
        this.hm = new HashMap<>();
        this.propertyContainer = new HashMap<>();
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz, Class<K> idClazz, String idProperty) throws Exception {
        try {
            if(!clazz.getDeclaredField(idProperty).getType().equals(idClazz))
                throw new Exception("Id class and id name doesn't match");
        } catch (NoSuchFieldException e) {
            throw e;
        }
        ModelService<T, K> modelService = new ModelServiceImpl<>();
        modelService.setClass(clazz);
        modelService.setIdClazz(idClazz);
        modelService.setIdProperty(idProperty);
        hm.put(name, modelService);
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz) throws Exception {

        if(this.hm.containsKey(name))
            return;
        Map<Object, Object> propertyMap = new HashMap<>();
        Map<Object, Object> idMap = new HashMap<>();
        List<Map<Object, Object>> lt = Arrays.asList(idMap, propertyMap);
        boolean found = false;

        ModelService<T, K> modelService = new ModelServiceImpl<>();
        for(Field field : clazz.getDeclaredFields()){
            propertyMap.put(field.getName(), TypeConverter.convertToAngularTypeName(field.getType().getSimpleName()));
            if(!found){
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations)
                {
                    if(annotation.annotationType().equals(Id.class))
                    {
                        String idName = field.getName();
                        modelService.setIdProperty(idName);
                        modelService.setIdClazz((Class<K>) field.getType());
                        modelService.setClass(clazz);
                        idMap.put("id", idName);
                        this.hm.put(name, modelService);
                        this.propertyContainer.put(name, lt);
                        found = true;
                        break;
                    }
                }
            }
        }
        if(!this.propertyContainer.containsKey(name))
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

    public Map<Object, List<Map<Object, Object>>> getProperties(){
        return Collections.unmodifiableMap(this.propertyContainer);
    }
}
