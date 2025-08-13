package com.challenger.itau.Desafio.Itau.Controller;

import com.challenger.itau.Desafio.Itau.Exception.TransactionException;
import com.challenger.itau.Desafio.Itau.Model.EstatisticaModel;
import com.challenger.itau.Desafio.Itau.Model.ListaTransacoesModel;
import com.challenger.itau.Desafio.Itau.Model.TransacaoModel;
import com.challenger.itau.Desafio.Itau.Service.TransacaoService;
import com.challenger.itau.Desafio.Itau.Utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;

@Slf4j
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
    public ResponseEntity<ApiResponse> transacaoPost (@RequestBody(required = false) String json) throws JsonProcessingException, ParseException {
            if (json.trim().isEmpty() || json == null) throw new TransactionException(new ApiResponse(HttpStatus.BAD_REQUEST).getStatusCode());
        try{
            TransacaoModel transacaoModel = objMapper.readValue(json,TransacaoModel.class);
            log.info(transacaoModel.toString());
            Boolean resultTransaction = this.transacaoService.transacao(transacaoModel);
            if (!resultTransaction) throw  new TransactionException(new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY).getStatusCode());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED).getStatusCode());
        }catch (TransactionException e) {
            log.info("Erro: " + e.toString());
            throw new TransactionException(new ApiResponse(e.getStatusCode()).getStatusCode());
        }
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<ApiResponse> transacaoDelete(){
        transacaoService.deletaTransacao();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK).getStatusCode());
    }

    @PostMapping("/transacao/segundos")
    public ResponseEntity<ApiResponse> segundosPost(@RequestBody String json) throws JsonProcessingException {
        JsonNode jsonNode = objMapper.readValue(json, JsonNode.class);
        log.info(String.valueOf(jsonNode.get("segundos")));
        this.transacaoService.limiteTempo(Integer.parseInt(String.valueOf(jsonNode.get("segundos"))));
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK).getStatusCode());
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaModel> transacaoGet() {
        return ResponseEntity.ok(this.transacaoService.visualizaEstatistica());
    }

}
