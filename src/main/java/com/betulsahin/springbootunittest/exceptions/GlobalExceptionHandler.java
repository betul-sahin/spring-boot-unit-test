package com.betulsahin.springbootunittest.exceptions;

import com.betulsahin.springbootunittest.dto.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IdentificationNumberNotValidException.class})
    @ResponseBody
    public AppErrorResponse handleException(IdentificationNumberNotValidException ex){
        AppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityExistsException.class})
    @ResponseBody
    public AppErrorResponse handleException(EntityExistsException ex){
        AppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseBody
    public AppErrorResponse handleException(EntityNotFoundException ex){
        AppErrorResponse response = prepareErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return response;
    }

    private AppErrorResponse prepareErrorResponse(HttpStatus badRequest, String exceptionMessage) {
        AppErrorResponse response = new AppErrorResponse();
        response.setStatus(badRequest.value());
        response.setMessage(exceptionMessage);
        response.setTimestamp(System.currentTimeMillis());

        return response;
    }
}
