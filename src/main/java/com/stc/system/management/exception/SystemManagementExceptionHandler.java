package com.stc.system.management.exception;

import com.stc.system.management.Vo.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SystemManagementExceptionHandler {

    @ExceptionHandler(PermissionNotAllowedException.class)
    public ResponseEntity<ResponseModel> permissionNotAllowedException(PermissionNotAllowedException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.BAD_REQUEST, e.getMessage(), "1", null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseModel> entityNoFoundException(EntityNotFoundException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.BAD_REQUEST, e.getMessage(), "1", null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }
}
