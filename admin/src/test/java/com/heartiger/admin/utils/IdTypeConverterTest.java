package com.heartiger.admin.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class IdTypeConverterTest {

    private String numberTestString;
    private String testString;

    @Before
    public void setup(){
        numberTestString = "5";
        testString = "Test string";
    }

    @Test
    public void convertStringNumberToIntegerShouldSuccess() {
        int expectedValue = 5;
        Integer result = IdTypeConverter.convert(numberTestString, Integer.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.intValue(), expectedValue);
    }

    @Test
    public void convertStringNumberToLongShouldSuccess() {
        long expectedValue = 5;
        Long result = IdTypeConverter.convert(numberTestString, Long.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.longValue(), expectedValue);
    }

    @Test
    public void convertStringNumberToStringShouldSuccess() {
        String result = IdTypeConverter.convert(numberTestString, String.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result, numberTestString);
    }

    @Test
    public void convertStringNumberToBooleanShouldReturnNull() {
        Boolean result = IdTypeConverter.convert(numberTestString, Boolean.class);
        Assert.assertNull(result);
    }

    @Test
    public void convertStringToIntegerShouldReturnNull() {
        Integer result = IdTypeConverter.convert(testString, Integer.class);
        Assert.assertNull(result);
    }
}