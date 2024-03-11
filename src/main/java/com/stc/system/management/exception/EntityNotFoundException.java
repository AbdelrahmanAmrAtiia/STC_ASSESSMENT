package com.stc.system.management.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName) {
        super(entityName + "Not Found");
    }
}
