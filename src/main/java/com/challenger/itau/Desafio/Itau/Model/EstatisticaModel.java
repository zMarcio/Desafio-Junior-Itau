package com.challenger.itau.Desafio.Itau.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EstatisticaModel {
    /* 
    "count": 10,
    "sum": 1234.56,
    "avg": 123.456,
    "min": 12.34,
    "max": 123.56 
    */

    private int contagem;
    private Double somaTotal;
    private Double media;
    private Double minimo;
    private Double maximo;


    @Override
    public String toString() {
        return "{" + 
        "count" + this.contagem + "," +
        "sum" + this.somaTotal + "," +
        "avg" + this.media + "," +
        "min" + this.minimo + "," +
        "max" + this.maximo + "," + 
        "}";
    }

}
