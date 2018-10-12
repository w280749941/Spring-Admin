package com.heartiger.admin.dto;

import com.heartiger.admin.enums.ResultEnum;

import java.io.Serializable;

public class ResponseDTO<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public ResponseDTO() {
    }

    public ResponseDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}