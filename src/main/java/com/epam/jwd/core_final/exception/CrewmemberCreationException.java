package com.epam.jwd.core_final.exception;

public class CrewmemberCreationException extends Exception {
    private final String message;

    public CrewmemberCreationException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
