package com.challenger.itau.Desafio.Itau.Utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ApiResponse {
    private HttpStatus statusCode;
    private Object mensagem;

    public ApiResponse(HttpStatus statusCode, Object mensagem) {
        this.statusCode = statusCode;
        this.mensagem = mensagem;
    }

    public ApiResponse(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
