package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEstabelecimento {

    static final String ESTABELECIMENTO_NAO_ENCONTRADO = "Estabelecimento com id %s não foi encontrado.";

    static final String SENHA_INVALIDA = "Senha invalida, por favor informe uma senha com 6 caracteres";

    static final String SENHA_INCORRETA = "Senha incorreta";

    public static ResponseEntity<CustomErrorType> erroEstabelecimentoNaoEncontrado(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEstabelecimento.ESTABELECIMENTO_NAO_ENCONTRADO, id)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroSenhaInvalida() {
        return new ResponseEntity<>(new CustomErrorType(SENHA_INVALIDA), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> senhaIncorreta() {
        return new ResponseEntity<>(new CustomErrorType(SENHA_INCORRETA), HttpStatus.FORBIDDEN);
    }
}