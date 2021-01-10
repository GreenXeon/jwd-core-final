package com.epam.jwd.core_final.exception;

public class MissionCreationException extends Exception {
    private final String message;

    public MissionCreationException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
