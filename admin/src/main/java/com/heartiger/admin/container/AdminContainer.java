package com.heartiger.admin.container;

import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.service.impl.ModelServiceImpl;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class AdminContainer {

    private Map<String, ModelService> hm;

    public AdminContainer(){
        this.hm = new HashMap<>();
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz, Class<K> idClazz){

        Reflections r = new Reflections(clazz.getName(), new FieldAnnotationsScanner());
        Set<Field> fields = r.getFieldsAnnotatedWith(Id.class);
        ModelService<T, K> modelService = new ModelServiceImpl<>();

        for(Field field : fields){
            if(field.getType() == idClazz)
            {
                modelService.setIdProperty(field.getName());
                break;
            }

        }
        modelService.setClass(clazz);
        modelService.setIdClazz(idClazz);
        hm.put(name, modelService);
    }

    public Map<String, ModelService> getContainer(){
        return this.hm;
    }

    public <T extends Serializable, K> ModelService<T, K> getService(String name){
        if(this.hm.containsKey(name))
            return this.hm.get(name);
        return null;
    }
}
