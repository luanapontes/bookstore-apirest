package com.bookstore.apirest.bookstoreapi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{

    private static final long SerialVersionUID = 1L;

    public ObjectNotFoundException (String message){
        super(message);
    }
}
