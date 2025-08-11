package com.challenger.itau.Desafio.Itau.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TransactionException extends RuntimeException{

    private final HttpStatus statusCode;

    public TransactionException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

}
