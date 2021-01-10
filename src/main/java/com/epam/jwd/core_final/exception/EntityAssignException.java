package com.epam.jwd.core_final.exception;

public class EntityAssignException extends Exception {
    private final String message;

    public EntityAssignException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
