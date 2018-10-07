package com.heartiger.admin.controller;

import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.datamodel.UserInfo;
import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.utils.IdTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "${admin.prefix.uri}")
public class ModelController {

    private final AdminContainer adminContainer;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ModelController(AdminContainer adminContainer, EntityManager entityManager){
        this.adminContainer = adminContainer;
        this.entityManager = entityManager;
        this.adminContainer.register("user", UserInfo.class, Integer.class);
        this.adminContainer.register("role", RoleInfo.class, Integer.class);
    }

    @GetMapping("/{entity}/{id}")
    public <T extends Serializable, K> T findEntityById(@PathVariable("entity") String entity,
                                                               @PathVariable("id") String id){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return null;
        modelService.setEntityManager(this.entityManager);
        K parsedId = IdTypeConverter.convert(id, modelService.getIdClazz());
        if(parsedId == null)
            return null;
        return modelService.findOne(parsedId);
    }


    @GetMapping("/{entity}")
    public <T extends Serializable, K> List<T> findAllEntities(@PathVariable("entity") String entity){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        modelService.setEntityManager(this.entityManager);
        return modelService.findAll();
    }
}
