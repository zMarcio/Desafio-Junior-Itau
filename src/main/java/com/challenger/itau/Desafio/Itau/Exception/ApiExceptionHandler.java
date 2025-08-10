package com.challenger.itau.Desafio.Itau.Exception;

import com.challenger.itau.Desafio.Itau.Utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ApiResponse> handleTransactionException(TransactionException tra){
        return new ResponseEntity<>(new ApiResponse(tra.getStatusCode()).getStatusCode());
    }
}
