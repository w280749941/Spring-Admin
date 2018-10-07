package com.heartiger.admin.controller;

import com.heartiger.admin.AdminApplicationTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public class UserControllerTest extends AdminApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private String getUrl;

    @Before
    public void setup(){
        getUrl = "/admin/user";
    }

    @Test
    public void findAllShouldReturnStatusCode200AndNotNull() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void findOneShouldReturnStatusCode200AndNotNull() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getUrl+"/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }
}