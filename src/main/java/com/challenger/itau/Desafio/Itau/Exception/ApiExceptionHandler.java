package com.challenger.itau.Desafio.Itau.Exception;

import com.challenger.itau.Desafio.Itau.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Void> handleTransactionException(TransactionException tra){
        return ResponseEntity.status(tra.getStatusCode()).build();
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 sem corpo
    }
}
