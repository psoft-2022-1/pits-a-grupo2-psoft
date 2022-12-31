package br.com.ufcg.ccc.psoft.util;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEntregador {

    static final String ENTREGADOR_NAO_CASTRADO = "Entregador com id %s não está cadastrado";

    static final String ENTREGADORES_NAO_CASTRADOS = "Não há entregadores cadastrados";

    static final String ENTREGADOR_JA_CADASTRADO = "O entregador nome %s já esta cadastrado";

    static final String CODIGO_ACESSO_INVALIDO = "O código de acesso deve possuir 6 dígitos.";

    static final String ENTREGADOR_NAO_APROVADO = "O entregador com id %s não foi aprovado ainda pelo estabelecimento";

    public static ResponseEntity<CustomErrorType> erroEntregadorNaoEncontrado(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_NAO_CASTRADO, id)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.CODIGO_ACESSO_INVALIDO),
                HttpStatus.NOT_ACCEPTABLE);
    }
    public static ResponseEntity<CustomErrorType> erroSemEntregadoresCadastrados() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.ENTREGADORES_NAO_CASTRADOS),
                HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<?> erroEntregadorJaCadastrado(EntregadorRequestDTO entregadorRequestDTO) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_JA_CADASTRADO,
                entregadorRequestDTO.getNomeCompleto())), HttpStatus.CONFLICT);
    }
    public static ResponseEntity<?> erroEntregadoratualizaStatusDisponibilidade(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_NAO_APROVADO, id)), HttpStatus.CONFLICT);
    }
}
