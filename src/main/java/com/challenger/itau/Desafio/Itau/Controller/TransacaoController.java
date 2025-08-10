package com.challenger.itau.Desafio.Itau.Controller;

import com.challenger.itau.Desafio.Itau.Exception.TransactionException;
import com.challenger.itau.Desafio.Itau.Model.ListaTransacoesModel;
import com.challenger.itau.Desafio.Itau.Model.TransacaoModel;
import com.challenger.itau.Desafio.Itau.Service.TransacaoService;
import com.challenger.itau.Desafio.Itau.Utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequestMapping("/api")
public class TransacaoController {



    /*
        Tenham os campos valor e dataHora preenchidos (Controller Reponsability)
        A transação DEVE ter valor igual ou maior que 0 (zero) (Controller Responsability)


        201 Created sem nenhum corpo
            A transação foi aceita (ou seja foi validada, está válida e foi registrada) (Service)
        422 Unprocessable Entity sem nenhum corpo
            A transação não foi aceita por qualquer motivo
            (1 ou mais dos critérios de aceite não foram atendidos
            - por exemplo: uma transação com valor menor que 0) (Service)
        400 Bad Request sem nenhum corpo
            A API não compreendeu a requisição do cliente (por exemplo: um JSON inválido) (Controller)
    */

    @Autowired
    private TransacaoService transacaoService;

    private final ObjectMapper objMapper;

    public TransacaoController(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

    @PostMapping("/transacao")
    public ResponseEntity<ApiResponse> transacaoController (@RequestBody(required = false) String json) throws JsonProcessingException, ParseException {

        if (json == null || json.trim().isEmpty()) throw new TransactionException(new ApiResponse(HttpStatus.BAD_REQUEST).getStatusCode());

        TransacaoModel transacaoModel = objMapper.readValue(json,TransacaoModel.class);

        Boolean resultTransaction = this.transacaoService.transacao(transacaoModel);

        if (!resultTransaction) throw new TransactionException(new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY).getStatusCode());

        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED).getStatusCode());

    }

}
