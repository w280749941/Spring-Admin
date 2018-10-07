package com.heartiger.admin.container;

import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.service.impl.ModelServiceImpl;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminContainer {

    private Map<String, ModelService> hm;

    public AdminContainer(){
        this.hm = new HashMap<>();
    }

    public <T extends Serializable, K> void register(String name, Class<T> clazz, Class<K> idClazz){
        ModelService<T, K> modelService = new ModelServiceImpl<>();
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
