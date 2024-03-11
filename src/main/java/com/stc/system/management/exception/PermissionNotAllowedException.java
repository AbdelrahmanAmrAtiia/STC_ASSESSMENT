package com.stc.system.management.exception;

public class PermissionNotAllowedException extends RuntimeException{
    public PermissionNotAllowedException() {
        super("Action Not Allowed");
    }
}
