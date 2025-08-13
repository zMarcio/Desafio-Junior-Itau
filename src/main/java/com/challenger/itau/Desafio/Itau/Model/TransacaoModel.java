package com.challenger.itau.Desafio.Itau.Model;


import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties({"dataTransacao"})
@NoArgsConstructor
@ToString
public class TransacaoModel {
    @JsonProperty("valor")
    @NonNull
    private Double valor;
    
    @JsonProperty("dataHora")
    @NonNull
    private OffsetDateTime dataHora;

    @NonNull
    private OffsetDateTime dataTransacao;


}
