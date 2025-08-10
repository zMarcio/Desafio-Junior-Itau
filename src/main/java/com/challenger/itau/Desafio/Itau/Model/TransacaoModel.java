package com.challenger.itau.Desafio.Itau.Model;


import java.time.OffsetDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.ModelAttribute;

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
