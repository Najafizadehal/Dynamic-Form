package com.saman.Form.shared;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FormException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public FormException(String message, HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
