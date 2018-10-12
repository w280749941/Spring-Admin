package com.heartiger.admin.utils;

import com.heartiger.admin.dto.ResponseDTO;
import com.heartiger.admin.enums.ResultEnum;

public class ResultDTOUtil {

    public static ResponseDTO<Object> success() {
        return success(null);
    }

    public static <T> ResponseDTO<T> success(T object) {
        ResponseDTO<T> resultDTO = new ResponseDTO<T>(ResultEnum.ACTION_SUCCESS);
        resultDTO.setData(object);
        return resultDTO;
    }

    public static ResponseDTO error(Integer code, String message) {
        ResponseDTO resultDTO = new ResponseDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResponseDTO error(ResultEnum resultEnum) {
        ResponseDTO resultDTO = new ResponseDTO();
        resultDTO.setCode(resultEnum.getCode());
        resultDTO.setMessage(resultEnum.getMessage());
        return resultDTO;
    }
}