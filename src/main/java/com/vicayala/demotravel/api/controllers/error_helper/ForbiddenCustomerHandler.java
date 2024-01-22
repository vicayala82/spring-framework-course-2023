package com.vicayala.demotravel.api.controllers.error_helper;

import com.vicayala.demotravel.api.models.response.BaseErrorResponse;
import com.vicayala.demotravel.api.models.response.ErrorResponse;
import com.vicayala.demotravel.util.exceptions.ForbiddenCustomerException;
import com.vicayala.demotravel.util.exceptions.IdNotFoundExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundExceptions exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
