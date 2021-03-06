package com.barry.groceryposkata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateItemException extends Exception {

    public DuplicateItemException(String message){
        super(message);
    }
}
