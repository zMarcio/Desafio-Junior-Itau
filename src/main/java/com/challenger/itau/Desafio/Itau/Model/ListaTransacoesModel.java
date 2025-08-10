package com.challenger.itau.Desafio.Itau.Model;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class ListaTransacoesModel {
    private List<TransacaoModel> transacaoModelList = new ArrayList<>();

    public void adicionarTransacao(TransacaoModel transacaoModel) {
        this.transacaoModelList.add(transacaoModel);
    }
}
