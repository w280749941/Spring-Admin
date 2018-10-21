package com.heartiger.admin.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TypeConverterTest {

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
        Integer result = TypeConverter.convert(numberTestString, Integer.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.intValue(), expectedValue);
    }

    @Test
    public void convertStringNumberToLongShouldSuccess() {
        long expectedValue = 5;
        Long result = TypeConverter.convert(numberTestString, Long.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.longValue(), expectedValue);
    }

    @Test
    public void convertStringNumberToStringShouldSuccess() {
        String result = TypeConverter.convert(numberTestString, String.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result, numberTestString);
    }

    @Test
    public void convertStringNumberToBooleanShouldReturnNull() {
        Boolean result = TypeConverter.convert(numberTestString, Boolean.class);
        Assert.assertNull(result);
    }

    @Test
    public void convertStringToIntegerShouldReturnNull() {
        Integer result = TypeConverter.convert(testString, Integer.class);
        Assert.assertNull(result);
    }
}