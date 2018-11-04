package com.heartiger.admin.service;

import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.datamodel.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class ModelServiceTest extends AdminApplicationTests {

    @Autowired
    private AdminContainer adminContainer;
    @PersistenceContext
    private EntityManager entityManager;

    private ModelService<UserInfo, Integer> modelService;

    @Before
    public void setup() throws Exception {
        adminContainer.register("user", UserInfo.class);
        this.modelService = adminContainer.getService("user");
        this.modelService.setEntityManager(this.entityManager);
    }

    @Test
    public void testFindAll() {
        List result = this.modelService.findAll();
        Assert.assertTrue("Returned an empty list of user", result.size() > 0);
    }

    @Test
    public void findOne() {
        Object result = this.modelService.findOne(2);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void create() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("adff@email.com");
        userInfo.setPasscode("asfb");
        Object result = this.modelService.create(userInfo);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void deleteById() {
        this.modelService.deleteById(2);
        UserInfo result = this.modelService.findOne(2);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void delete() {
        UserInfo userInfo = this.modelService.findOne(2);
        this.modelService.delete(userInfo);
        Object result = this.modelService.findOne(2);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void update() {
        String email = "Update@gmial.com";
        UserInfo userInfo = this.modelService.findOne(2);
        userInfo.setEmail(email);
        userInfo = this.modelService.update(userInfo);
        Assert.assertEquals("Email Found", userInfo.getEmail(), email);
    }

    @Test
    public void getIdPropertyShouldNotReturnNull() {
        Assert.assertNotNull(this.modelService.getIdProperty());
    }

    @Test
    public void getIdClassShouldNotReturnNull() {
        Assert.assertNotNull(this.modelService.getIdClazz());
    }


    @Test
    public void getClassShouldNotReturnNull() {
        Assert.assertNotNull(this.modelService.getClazz());
    }

    @Test
    public void getPageableServiceShouldNotReturnNull() {
        Assert.assertNotNull(this.modelService.getPageableService());
    }

    @Test
    public void getEntityManagerShouldNotReturnNull() {
        Assert.assertNotNull(this.modelService.getEntityManager());
    }
}