package com.bookstore.apirest.bookstoreapi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectAlreadyExistException extends RuntimeException{

    public ObjectAlreadyExistException() {
        super("Object already exists");
    }
}
