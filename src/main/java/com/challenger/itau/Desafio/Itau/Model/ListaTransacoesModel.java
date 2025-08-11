package com.challenger.itau.Desafio.Itau.Model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class ListaTransacoesModel {
    private List<TransacaoModel> transacaoModelList = new ArrayList<>();

    public void adicionarTransacao(TransacaoModel transacaoModel) {
        this.transacaoModelList.add(transacaoModel);
    }

    public void removerTodasTransacoes(){
        this.transacaoModelList.clear();
    }
}
