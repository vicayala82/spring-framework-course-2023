package com.vicayala.demotravel.util.exceptions;

import com.vicayala.demotravel.util.ServiceConstants;

public class UsernameNotFoundExceptions extends RuntimeException{

    private static final String ERROR_MESSAGE= "User not found %s in %s";

    public UsernameNotFoundExceptions(String username){
        super(String.format(ERROR_MESSAGE, username, ServiceConstants.COLLECTION_NAMES));
    }
}
