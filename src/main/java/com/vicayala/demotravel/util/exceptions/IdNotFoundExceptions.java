package com.vicayala.demotravel.util.exceptions;

public class IdNotFoundExceptions extends RuntimeException{

    private static final String ERROR_MESSAGE= "Record not found in %s";

    public IdNotFoundExceptions(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
