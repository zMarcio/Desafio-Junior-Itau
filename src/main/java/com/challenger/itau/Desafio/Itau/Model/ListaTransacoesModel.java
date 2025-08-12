package com.challenger.itau.Desafio.Itau.Model;


import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

@ToString
@Getter
@Setter
public class ListaTransacoesModel {
    private List<TransacaoModel> transacaoModelList = new ArrayList<>();

    public int segundos;

    public void adicionarTransacao(TransacaoModel transacaoModel) {
        this.transacaoModelList.add(transacaoModel);
    }

    public void removerTodasTransacoes(){
        this.transacaoModelList.clear();
    }

    // To do 
    public void criandoEstatistica(){
        DoubleSummaryStatistics statistics = this.transacaoModelList.stream().filter(t -> t.getDataTransacao().isBefore(OffsetDateTime.now().minus(this.getSegundos())))
    }


}
