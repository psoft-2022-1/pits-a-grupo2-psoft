package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEstabelecimento {

    static final String ESTABELECIMENTO_NAO_ENCONTRADO = "Estabelecimento com id %s não foi encontrado.";

    public static ResponseEntity<CustomErrorType> erroEstabelecimentoNaoEncontrado(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEstabelecimento.ESTABELECIMENTO_NAO_ENCONTRADO, id)),
                HttpStatus.NOT_FOUND);
    }
}
