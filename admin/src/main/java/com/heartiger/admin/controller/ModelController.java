package com.heartiger.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.dto.ResponseDTO;
import com.heartiger.admin.enums.ResultEnum;
import com.heartiger.admin.service.ModelService;
import com.heartiger.admin.service.PageableService;
import com.heartiger.admin.utils.TypeConverter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
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

    @GetMapping("/entity/{entity}/{id}")
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findEntityById(@PathVariable("entity") String entity,
                                                                                  @PathVariable("id") String id){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        K parsedId;
        try {
            parsedId = TypeConverter.convert(id, modelService.getIdClazz());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.PARAMS_ERROR));
        }

        if(parsedId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.ENTRY_NOT_FOUND));
        return ResponseEntity.ok(ResultDTOUtil.success(modelService.findOne(parsedId)));
    }


    @GetMapping("/entity/{entity}")
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findAllEntities(@PathVariable("entity") String entity){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);
        return ResponseEntity.ok(ResultDTOUtil.success(modelService.findAll()));
    }

    @DeleteMapping("/entity/{entity}/{id}")
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
            parsedId = TypeConverter.convert(id, modelService.getIdClazz());
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
        return ResponseEntity.ok(ResultDTOUtil.success());
    }

    @PostMapping("/entity/{entity}")
    @Transactional
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> createEntity(@PathVariable("entity") String entity,
                                                                                @RequestBody Object requestBody) {

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

    @PutMapping("/entity/{entity}")
    @Transactional(dontRollbackOn = Exception.class)
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> updateEntity(
            @PathVariable("entity") String entity, @RequestBody Object requestBody)
            throws NoSuchFieldException, IllegalAccessException {

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

        K idValue = getId(modelService, entityToUpdate);

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

        ResponseEntity<ResponseDTO> errorResponse = null;
        if(idValue == null || modelService.findOne(idValue) == null){
            try{
                T createResult = modelService.create(entityToUpdate);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResultDTOUtil.success(createResult));
            }catch (Exception ex){
                errorResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResultDTOUtil.error(400, ex.getMessage()));
            }
        }
        return errorResponse == null
                ? ResponseEntity.ok(ResultDTOUtil.success(modelService.update(entityToUpdate)))
                : errorResponse;

    }

    private <T extends Serializable, K> K getId(ModelService<T, K> modelService, T entityToUpdate)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = entityToUpdate.getClass().getDeclaredField(modelService.getIdProperty());
        field.setAccessible(true);
        return (K) field.get(entityToUpdate);
    }

    @GetMapping("/entity/{entity}/page/all")
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findPages(@PathVariable("entity") String entity,
                                                                             @RequestParam int size){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        PageableService<T> pageableService = modelService.getPageableService();
        if(pageableService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultDTOUtil.error(ResultEnum.SERVER_ERROR));

        pageableService.init(size);
        pageableService.setCurrentPage(0);
        Map<Object, Object> hm = new HashMap<>();
        hm.put("pages", String.valueOf(pageableService.getMaxPages()));
        hm.put("content", pageableService.getCurrentResults());
        return ResponseEntity.ok(ResultDTOUtil.success(hm));
    }

    @GetMapping("/entity/{entity}/page")
    public <T extends Serializable, K> ResponseEntity<ResponseDTO> findPages(@PathVariable("entity") String entity,
                                                                             @RequestParam int size, @RequestParam int page){
        ModelService<T, K> modelService = adminContainer.getService(entity);
        if(modelService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResultDTOUtil.error(ResultEnum.INVALID_ENTITY_TYPE));
        modelService.setEntityManager(this.entityManager);

        PageableService<T> pageableService = modelService.getPageableService();
        if(pageableService == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultDTOUtil.error(ResultEnum.SERVER_ERROR));

        pageableService.init(size);
        pageableService.setCurrentPage(--page);

        return ResponseEntity.ok(ResultDTOUtil.success(pageableService.getCurrentResults()));
    }

    @GetMapping("/properties")
    public ResponseEntity<ResponseDTO> getProperty(){
       return ResponseEntity.ok(ResultDTOUtil.success(adminContainer.getProperties()));
    }
}
