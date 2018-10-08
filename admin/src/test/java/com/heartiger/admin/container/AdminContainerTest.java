package com.heartiger.admin.container;

import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.datamodel.UserInfo;
import com.heartiger.admin.service.ModelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class AdminContainerTest extends AdminApplicationTests {

    @Autowired
    private AdminContainer adminContainer;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setup(){
        //adminContainer = new AdminContainer();
        adminContainer.register("user", UserInfo.class, Integer.class);
        adminContainer.register("role", RoleInfo.class, Integer.class);
    }

    @Test
    public void findOne() {
        ModelService userService = adminContainer.getService("user");
        ModelService roleService = adminContainer.getService("role");
        userService.setEntityManager(entityManager);
        roleService.setEntityManager(entityManager);
        Object userInfo = userService.findOne(2);
        Object roleInfo = roleService.findOne(1);
        Assert.assertNotNull("Returned a null object", userInfo);
        Assert.assertNotNull("Returned a null object", roleInfo);
    }

}