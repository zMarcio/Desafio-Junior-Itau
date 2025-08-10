package com.challenger.itau.Desafio.Itau.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListaTransacaoDTO {
    private List<TransacaoDTO> transacaoDTO;
}
