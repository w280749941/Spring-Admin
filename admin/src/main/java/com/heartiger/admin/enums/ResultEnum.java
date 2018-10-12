package com.heartiger.admin.enums;

public enum ResultEnum {

    ACTION_SUCCESS(0, "Success"),
    PARAMS_ERROR(1, "Parameters are not correct"),
    SECURITY_DEFENSE_ERROR(100, "You should not bypass the security"),
    ENTRY_NOT_FOUND(104, "The data you are looking for is not found"),
    SERVER_ERROR(500, "Error Occured, report required"),
    API_NOT_FOUND(404, "Oops the api doesn't exist"),
    INVALID_ENTITY_TYPE(501, "There isn't a valid entity type registered"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
