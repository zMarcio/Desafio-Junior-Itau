package com.challenger.itau.Desafio.Itau.Service;

import com.challenger.itau.Desafio.Itau.DTO.ListaTransacaoDTO;
import com.challenger.itau.Desafio.Itau.DTO.TransacaoDTO;
import com.challenger.itau.Desafio.Itau.Model.ListaTransacoesModel;
import com.challenger.itau.Desafio.Itau.Model.TransacaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TransacaoService {


    private final ListaTransacoesModel lista = new ListaTransacoesModel();

    public Boolean transacao(TransacaoModel transacaoEnviada) throws ParseException {
        if(verificaTransacao(transacaoEnviada)){
            lista.adicionarTransacao(new TransacaoModel(transacaoEnviada.getValor(), transacaoEnviada.getDataHora(), OffsetDateTime.now()));
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
}
