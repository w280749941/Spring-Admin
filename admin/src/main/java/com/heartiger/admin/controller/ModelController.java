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
import java.lang.reflect.Field;
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

    //TODO REPLACE RESPONSE WITH RESPONSE DTO.
    //TODO ADD TEST FOR CONTROLLERS.
    @PostMapping("/{entity}")
    @Transactional
    public <T extends Serializable, K> T createEntity(@PathVariable("entity") String entity,
                                                @RequestBody Object requestBody) throws NoSuchFieldException, IllegalAccessException {

        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return null;
        modelService.setEntityManager(this.entityManager);

        T entityToCreate = objectMapper.convertValue(requestBody, modelService.getClazz());

        Object idValue = getId(modelService, entityToCreate);
        if(idValue != null) //"Please provide an entity without an ID to create"
            return null;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entityToCreate);

        for(ConstraintViolation constraintViolation : constraintViolations){
            System.out.println(constraintViolation.getMessage());
        }

        if(constraintViolations.size() > 0)
            return null;

        return modelService.create(entityToCreate);
    }

    @PutMapping("/{entity}")
    @Transactional
    public <T extends Serializable, K> T updateEntity(@PathVariable("entity") String entity,
                                                @RequestBody Object requestBody) throws NoSuchFieldException, IllegalAccessException {

        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return null;
        modelService.setEntityManager(this.entityManager);

        T entityToUpdate = objectMapper.convertValue(requestBody, modelService.getClazz());

        Object idValue = getId(modelService, entityToUpdate);

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entityToUpdate);

        for(ConstraintViolation constraintViolation : constraintViolations){
            System.out.println(constraintViolation.getMessage());
        }

        if(constraintViolations.size() > 0)
            return null;

        if(idValue == null)
            return modelService.create(entityToUpdate);

        return modelService.update(entityToUpdate);
    }

    private <T extends Serializable, K> Object getId(ModelService<T, K> modelService, T entityToUpdate) throws NoSuchFieldException, IllegalAccessException {
        Field field = entityToUpdate.getClass().getDeclaredField(modelService.getIdProperty());
        field.setAccessible(true);
        return field.get(entityToUpdate);
    }
}
