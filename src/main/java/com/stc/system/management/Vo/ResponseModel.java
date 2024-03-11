package com.stc.system.management.Vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
public class ResponseModel<T> {
    private Date responseDate;
    private HttpStatus httpStatus;
    private String message;
    private String errorCode;
    private T data;

    public ResponseModel(HttpStatus httpStatus, String message, String errorCode, T data) {
        this.responseDate = new Date();
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }
}
