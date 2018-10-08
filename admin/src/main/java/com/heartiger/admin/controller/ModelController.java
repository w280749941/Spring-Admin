package com.heartiger.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.utils.IdTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "${admin.prefix.uri}")
public class ModelController {

    private final AdminContainer adminContainer;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ModelController(AdminContainer adminContainer, EntityManager entityManager,
                           ObjectMapper objectMapper, Validator validator){
        this.adminContainer = adminContainer;
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
        this.validator = validator;
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

    @DeleteMapping("/{entity}/{id}")
    @Transactional
    public <T extends Serializable, K> void deleteEntityById(@PathVariable("entity") String entity,
                                                        @PathVariable("id") String id){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return;
        modelService.setEntityManager(this.entityManager);
        K parsedId = IdTypeConverter.convert(id, modelService.getIdClazz());
        if(parsedId == null)
            return;

        modelService.delete(modelService.findOne(parsedId));
    }

    @PostMapping("/{entity}")
    @Transactional
    public <T extends Serializable, K> T create(@PathVariable("entity") String entity,
                                                @RequestBody Object str){

        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return null;
        modelService.setEntityManager(this.entityManager);

        T entityToCreate = objectMapper.convertValue(str, modelService.getClazz());

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entityToCreate);

        for(ConstraintViolation constraintViolation : constraintViolations){
            System.out.println(constraintViolation.getMessage());
        }

        return modelService.create(entityToCreate);
    }

    @PostMapping("/static/{entity}")
    @Transactional
    public RoleInfo createbb(@PathVariable("entity") String entity, @RequestBody RoleInfo str){

        ModelService<RoleInfo, Integer> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return null;
        modelService.setEntityManager(this.entityManager);
        return modelService.create(str);
    }
}
