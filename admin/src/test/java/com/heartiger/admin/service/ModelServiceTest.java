package com.heartiger.admin.service;

import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.datamodel.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class ModelServiceTest extends AdminApplicationTests {

    @Autowired
    private ModelService<UserInfo, Integer> modelService;

    @Before
    public void setup(){
        Class clazzToSet = UserInfo.class;
        modelService.setClass(clazzToSet);
    }

    @Test
    public void testFindAll() {
        List result = modelService.findAll();
        Assert.assertTrue("Returned an empty list of user", result.size() > 0);
    }

    @Test
    public void findOne() {
        Object result = modelService.findOne(2);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void create() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("adff@email.com");
        userInfo.setPasscode("asfb");
        Object result = modelService.create(userInfo);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void deleteById() {
        modelService.deleteById(4);
        UserInfo result = modelService.findOne(4);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void delete() {
        UserInfo userInfo = modelService.findOne(2);
        modelService.delete(userInfo);
        Object result = modelService.findOne(2);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void update() {
        String email = "Update@gmial.com";
        UserInfo userInfo = modelService.findOne(2);
        userInfo.setEmail(email);
        userInfo = (UserInfo) modelService.update(userInfo);
        Assert.assertEquals("Email Found", userInfo.getEmail(), email);
    }
}