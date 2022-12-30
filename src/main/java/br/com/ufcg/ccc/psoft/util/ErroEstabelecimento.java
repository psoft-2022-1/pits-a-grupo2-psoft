package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEstabelecimento {

	static final String ESTABELECIMENTO_NAO_ENCONTRADO = "Estabelecimento com id %s não foi encontrado.";

	static final String CODIGO_ACESSO_INVALIDO = "O código de acesso deve possuir 6 dígitos.";

	public static ResponseEntity<CustomErrorType> erroEstabelecimentoNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEstabelecimento.ESTABELECIMENTO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEstabelecimento.CODIGO_ACESSO_INVALIDO),
				HttpStatus.NOT_ACCEPTABLE);
	}
}
