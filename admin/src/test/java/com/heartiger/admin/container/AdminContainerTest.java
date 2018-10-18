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

    @Test
    public void registerFullParamsShouldReturnSuccess() throws Exception {
        adminContainer.clearContainer();
        adminContainer.register("user", UserInfo.class, Integer.class, "userId");
        adminContainer.register("role", RoleInfo.class, Integer.class, "roleId");
        Assert.assertNotNull(adminContainer.getService("user"));
        Assert.assertNotNull(adminContainer.getService("role"));
    }

    @Test
    public void registerShouldReturnSuccess() throws Exception {
        adminContainer.clearContainer();
        adminContainer.register("user", UserInfo.class);
        adminContainer.register("role", RoleInfo.class);
        Assert.assertNotNull(adminContainer.getService("user"));
        Assert.assertNotNull(adminContainer.getService("role"));
    }

    @Test
    public void registerFullParamsWithWrongPropertyShouldThrowException(){
        adminContainer.clearContainer();
        try {
            adminContainer.register("user", UserInfo.class, Integer.class, "userId1");
            adminContainer.register("role", RoleInfo.class, Integer.class, "roleId1");
        } catch (Exception e) {
            Assert.assertNull(adminContainer.getService("user"));
            Assert.assertNull(adminContainer.getService("role"));
        }
    }
}