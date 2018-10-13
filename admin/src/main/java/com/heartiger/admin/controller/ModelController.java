package com.heartiger.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.dto.ResponseDTO;
import com.heartiger.admin.enums.ResultEnum;
import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.utils.IdTypeConverter;
import com.heartiger.admin.utils.ResultDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.lang.reflect.Field;
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
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findEntityById(@PathVariable("entity") String entity,
                                                                                  @PathVariable("id") String id){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        K parsedId;
        try {
            parsedId = IdTypeConverter.convert(id, modelService.getIdClazz());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.PARAMS_ERROR));
        }

        if(parsedId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.ENTRY_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultDTOUtil.success(modelService.findOne(parsedId)));
    }


    @GetMapping("/{entity}")
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findAllEntities(@PathVariable("entity") String entity){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultDTOUtil.success(modelService.findAll()));
    }

    @DeleteMapping("/{entity}/{id}")
    @Transactional
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> deleteEntityById(@PathVariable("entity") String entity,
                                                                                    @PathVariable("id") String id){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        K parsedId;
        try {
            parsedId = IdTypeConverter.convert(id, modelService.getIdClazz());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.PARAMS_ERROR));
        }

        if(parsedId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.ENTRY_NOT_FOUND));

        T entityFound = modelService.findOne(parsedId);
        if(entityFound == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.ENTRY_NOT_FOUND));

        modelService.delete(entityFound);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultDTOUtil.success());
    }

    @PostMapping("/{entity}")
    @Transactional
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> createEntity(@PathVariable("entity") String entity,
                                                                                @RequestBody Object requestBody) throws NoSuchFieldException, IllegalAccessException {

        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        T entityToCreate;
        try {
            entityToCreate = objectMapper.convertValue(requestBody, modelService.getClazz());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.PARAMS_ERROR));
        }

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entityToCreate);

        StringBuilder sb = new StringBuilder();
        for(ConstraintViolation constraintViolation : constraintViolations)
        {
            sb.append(constraintViolation.getMessage());
            sb.append(System.getProperty("line.separator"));
        }
        if(constraintViolations.size() > 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(400, sb.toString()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTOUtil.success(modelService.create(entityToCreate)));
    }

    @PutMapping("/{entity}")
    @Transactional
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> updateEntity(@PathVariable("entity") String entity, @RequestBody Object requestBody) throws NoSuchFieldException, IllegalAccessException {

        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        T entityToUpdate;
        try {
            entityToUpdate = objectMapper.convertValue(requestBody, modelService.getClazz());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.PARAMS_ERROR));
        }

        Object idValue = getId(modelService, entityToUpdate);

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entityToUpdate);

        StringBuilder sb = new StringBuilder();
        for(ConstraintViolation constraintViolation : constraintViolations)
        {
            sb.append(constraintViolation.getMessage());
            sb.append(System.getProperty("line.separator"));
        }
        if(constraintViolations.size() > 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(400, sb.toString()));

        if(idValue == null)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResultDTOUtil.success(modelService.create(entityToUpdate)));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultDTOUtil.success(modelService.update(entityToUpdate)));
    }

    private <T extends Serializable, K> Object getId(ModelService<T, K> modelService, T entityToUpdate) throws NoSuchFieldException, IllegalAccessException {
        Field field = entityToUpdate.getClass().getDeclaredField(modelService.getIdProperty());
        field.setAccessible(true);
        return field.get(entityToUpdate);
    }
}
