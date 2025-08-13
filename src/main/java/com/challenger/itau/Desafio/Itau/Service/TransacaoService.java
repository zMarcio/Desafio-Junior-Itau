package com.challenger.itau.Desafio.Itau.Service;

import com.challenger.itau.Desafio.Itau.Model.EstatisticaModel;
import com.challenger.itau.Desafio.Itau.Model.ListaTransacoesModel;
import com.challenger.itau.Desafio.Itau.Model.TransacaoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.OffsetDateTime;

@Service
@Slf4j
public class TransacaoService {
    private final ListaTransacoesModel lista = new ListaTransacoesModel();

    public Boolean transacao(TransacaoModel transacaoEnviada) throws ParseException {
        if(verificaTransacao(transacaoEnviada)){
            lista.adicionarTransacao(new TransacaoModel(transacaoEnviada.getValor(), transacaoEnviada.getDataHora(), OffsetDateTime.now()));
            log.info(lista.toString());
            return true;
        }
        return false;
    }

    private Boolean verificaTransacao(TransacaoModel transacaoEnviada) throws ParseException {
        /*
            DEVE:
            A transação DEVE ter acontecido a qualquer momento no passado (Service Responsability)
            NÃO DEVE:
            A transação NÃO DEVE acontecer no futuro (Service Responsability)
            A transação NÃO DEVE ter valor negativo (Service Responsability)
        */
        return transacaoEnviada.getValor() > 0 && !OffsetDateTime.now().isBefore(transacaoEnviada.getDataHora());
    }

    public boolean deletaTransacao() {
        log.info(lista.toString());
        this.lista.removerTodasTransacoes();
        log.info(lista.toString());
        return true;
    }

    public void limiteTempo(int segundos) {
        this.lista.setSegundos(segundos);
        log.info(String.valueOf(lista.getSegundos()));
    }

    public EstatisticaModel visualizaEstatistica(){
        return this.lista.criandoEstatistica();
    }
}
