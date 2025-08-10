package com.challenger.itau.Desafio.Itau.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private HttpStatus statusCode;
}
