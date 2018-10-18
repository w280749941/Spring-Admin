package com.heartiger.admin.service;

import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.container.AdminContainer;
import com.heartiger.admin.datamodel.RoleInfo;
import com.heartiger.admin.datamodel.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

public class PageableServiceTest extends AdminApplicationTests {

    @Autowired
    private AdminContainer adminContainer;

    @PersistenceContext
    private EntityManager entityManager;

    private PageableService<RoleInfo> pageableService;

    @Before
    public void setup() throws Exception {
        adminContainer.register("role", RoleInfo.class);
        ModelService<RoleInfo, Integer> modelService = adminContainer.getService("role");
        modelService.setEntityManager(this.entityManager);

        this.pageableService = modelService.getPageableService();
        pageableService.init(2);
    }

    @Test
    public void GetPageSizeShouldReturnAValue() {
        Assert.assertNotEquals(pageableService.getPageSize(), 0);
    }

    @Test
    public void GetMaxPagesShouldReturnAValue() {
        Assert.assertNotEquals(pageableService.getMaxPages(), 0);
    }

    @Test
    public void GetCurrentResultsShouldReturnNotNull() {
        Assert.assertNotNull(pageableService.getCurrentResults());
    }

    @Test
    public void GetCurrentPageShouldNotEqualZero() {
        pageableService.next();
        Assert.assertNotEquals(pageableService.getCurrentPage(), 0);
    }

    @Test
    public void GetPreviousPageShouldNotReturnNegativeValue(){
        pageableService.previous();
        Assert.assertEquals(pageableService.getCurrentPage(), 0);
    }
}