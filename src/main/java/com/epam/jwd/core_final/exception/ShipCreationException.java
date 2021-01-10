package com.epam.jwd.core_final.exception;

public class ShipCreationException extends Exception {
    private final String message;

    public ShipCreationException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
