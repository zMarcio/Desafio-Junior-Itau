package com.challenger.itau.Desafio.Itau.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
public class ListaTransacoesModel {
    private List<TransacaoModel> transacaoModelList = new ArrayList<>();

    private int segundos = 60;

    public void adicionarTransacao(TransacaoModel transacaoModel) {
        this.transacaoModelList.add(transacaoModel);
    }

    public void removerTodasTransacoes(){
        this.transacaoModelList.clear();
    }

    // To do
    public EstatisticaModel criandoEstatistica(){
        DoubleSummaryStatistics statistics = this.transacaoModelList.stream()
                .filter(t -> t.getDataTransacao().isAfter(OffsetDateTime.now().minusSeconds(this.getSegundos())))
                .mapToDouble(TransacaoModel::getValor)
                .summaryStatistics();

        if(this.transacaoModelList.isEmpty()) return new EstatisticaModel(0,0.0,0.0,0.0,0.0);
        log.info(new EstatisticaModel((int) statistics.getCount(), statistics.getSum(), statistics.getAverage(), statistics.getMin(), statistics.getMax()).toString());
        return new EstatisticaModel((int) statistics.getCount(), statistics.getSum(), statistics.getAverage(), statistics.getMin(), statistics.getMax());
    }


}
