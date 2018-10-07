package com.heartiger.admin.service;

import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.datamodel.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class RoleServiceTest extends AdminApplicationTests {

    @Autowired
    private ModelService<RoleInfo, Integer> modelService;

    @Before
    public void setup(){
        Class clazzToSet = RoleInfo.class;
        modelService.setClass(clazzToSet);
    }

    @Test
    public void testFindAll() {
        List result = modelService.findAll();
        Assert.assertTrue("Returned an empty list of roles", result.size() > 0);
    }

    @Test
    public void findOne() {
        Object result = modelService.findOne(1);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void create() {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName("user2");
        Object result = modelService.create(roleInfo);
        Assert.assertNotNull("Returned a null object", result);
    }

    @Test
    @Transactional
    public void deleteById() {
        modelService.deleteById(1);
        Object result = modelService.findOne(1);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void delete() {
        RoleInfo roleInfo = modelService.findOne(1);
        modelService.delete(roleInfo);
        Object result = modelService.findOne(1);
        Assert.assertNull("Delete unsuccessful", result);
    }

    @Test
    @Transactional
    public void update() {
        String roleName = "Admin";
        RoleInfo roleInfo = modelService.findOne(1);
        roleInfo.setRoleName(roleName);
        roleInfo = modelService.update(roleInfo);
        Assert.assertEquals("Email Found", roleInfo.getRoleName(), roleName);
    }
}