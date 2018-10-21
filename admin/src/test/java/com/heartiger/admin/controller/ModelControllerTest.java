package com.heartiger.admin.controller;

import com.google.gson.Gson;
import com.heartiger.admin.AdminApplicationTests;
import com.heartiger.admin.datamodel.UserInfo;
import com.heartiger.admin.dto.ResponseDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

@AutoConfigureMockMvc
public class ModelControllerTest extends AdminApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private Logger logger = LoggerFactory.getLogger(ModelControllerTest.class);

    private String getUrl;
    private String deleteUrl;
    private String findUrl;
    private String pageUrl;
    private String pageAllUrl;

    @Before
    public void setup(){
        String urlPrefix = "/admin/entity/user/";
        getUrl = urlPrefix;
        findUrl = urlPrefix + "2";
        deleteUrl = urlPrefix + "2";
        pageUrl = urlPrefix + "page";
        pageAllUrl = urlPrefix + "page/all";
    }

    @Test
    public void findAllShouldReturnStatusCode200AndNotNull() throws Exception {
        PerformGetRequestTest(getUrl);
    }

    @Test
    public void findOneShouldReturnStatusCode200AndNotNull() throws Exception {

        PerformGetRequestTest(findUrl);
    }

    private void PerformGetRequestTest(String findUrl) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(findUrl)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);
        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultDTO.getData());
    }

    @Test
    @Transactional
    public void deleteUserByIdShouldReturn200AndSuccessMessage() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(deleteUrl)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);
        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertNull(resultDTO.getData());
    }

    @Test
    @Transactional
    public void CreateShouldReturnStatusCode200AndNotNull() throws Exception {

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("test@email.com");
        userInfo.setPasscode("something");
        userInfo.setFirstName("test first");
        userInfo.setLastName("test last");

        String entityToCreate = gson.toJson(userInfo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(getUrl).contentType(MediaType.APPLICATION_JSON)
                .content(entityToCreate)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);

        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultDTO.getData());
    }

    @Test
    @Transactional
    public void UpdateShouldReturnStatusCode200AndNotNull() throws Exception {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(2);
        userInfo.setEmail("Some@email.com");
        userInfo.setPasscode("something");
        userInfo.setFirstName("test first");
        userInfo.setLastName("test last");

        String entityToCreate = gson.toJson(userInfo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(getUrl).contentType(MediaType.APPLICATION_JSON)
                .content(entityToCreate)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);
        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultDTO.getData());
    }

    @Test
    @Transactional
    public void PageShouldReturnStatusCode200AndNotNull() throws Exception {
        MultiValueMap<String, String> mp = new HttpHeaders();
        mp.add("size", "2");
        mp.add("page", "1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(pageUrl).params(mp)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);
        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultDTO.getData());
    }

    @Test
    @Transactional
    public void PageAllShouldReturnStatusCode200AndNotNull() throws Exception {
        MultiValueMap<String, String> mp = new HttpHeaders();
        mp.add("size", "2");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(pageAllUrl).params(mp)
                .accept(MediaType.APPLICATION_JSON);

        Object[] results = parseJsonResult(requestBuilder);
        MvcResult result = (MvcResult) results[0];
        ResponseDTO resultDTO = (ResponseDTO) results[1];

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultDTO.getData());
    }

    private Object[] parseJsonResult(RequestBuilder requestBuilder) throws Exception {
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ResponseDTO resultDTO = new ResponseDTO();
        try{
            resultDTO = gson.fromJson(result.getResponse().getContentAsString(), ResponseDTO.class);
        }catch (Exception e) {
            logger.error("Failed to parse json", e.getMessage());
        }

        Object[] results = new Object[2];
        results[0] = result;
        results[1] = resultDTO;

        return results;
    }
}