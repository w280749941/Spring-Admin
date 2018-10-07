package com.heartiger.admin.utils;

public class IdTypeConverter {

    public static <T> T convert(String id, Class<T> returnClass){
        try{
            if(returnClass == Integer.class)
                return (T) Integer.valueOf(id);
            else if (returnClass == Long.class)
                return (T) Long.valueOf(id);
            else if (returnClass == String.class)
                return (T) id;
            return null;
        } catch (NumberFormatException ex){
            return null;
        }
    }
}
