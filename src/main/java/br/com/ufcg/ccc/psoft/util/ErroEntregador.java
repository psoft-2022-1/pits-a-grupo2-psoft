package br.com.ufcg.ccc.psoft.util;

import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEntregador {

    static final String ENTREGADOR_NAO_CASTRADO = "Entregador com id %s não está cadastrado";

    static final String ENTREGADORES_NAO_CASTRADOS = "Não há entregadores cadastrados";

    static final String ENTREGADOR_JA_CADASTRADO = "O entregador nome %s já esta cadastrado";

    static final String SENHA_INVALIDA = "Senha invalida, por favor informe uma senha com 6 caracteres";

    static final String SENHA_INCORRETA = "Senha incorreta";

    public static ResponseEntity<CustomErrorType> erroEntregadorNaoEncontrado(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_NAO_CASTRADO, id)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroSemEntregadoresCadastrados() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.ENTREGADORES_NAO_CASTRADOS),
                HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<?> erroEntregadorJaCadastrado(EntregadorDTO entregadorDTO) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_JA_CADASTRADO,
                entregadorDTO.getNomeCompleto())), HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomErrorType> erroSenhaInvalida() {
        return new ResponseEntity<>(new CustomErrorType(SENHA_INVALIDA), HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity<CustomErrorType> senhaIncorreta() {
        return new ResponseEntity<>(new CustomErrorType(SENHA_INCORRETA), HttpStatus.FORBIDDEN);
    }
}