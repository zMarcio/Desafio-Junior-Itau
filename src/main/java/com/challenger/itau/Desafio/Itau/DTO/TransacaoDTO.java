package com.challenger.itau.Desafio.Itau.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class TransacaoDTO {
    private Double valor;
    private Date dataHora;
    private Date dataTransacao;
}
