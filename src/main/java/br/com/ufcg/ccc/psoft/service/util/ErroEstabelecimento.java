package br.com.ufcg.ccc.psoft.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEstabelecimento {

	static final String ESTABELECIMENTO_NAO_ENCONTRADO = "Estabelecimento com id %s não foi encontrado.";

	static final String CODIGO_ACESSO_INVALIDO = "O código de acesso deve possuir 6 dígitos.";

	static final String CODIGO_ACESSO_INCORRETO = "Código de acesso incorreto. Tente novamente!";

	public static ResponseEntity<CustomErrorType> erroEstabelecimentoNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEstabelecimento.ESTABELECIMENTO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEstabelecimento.CODIGO_ACESSO_INVALIDO),
				HttpStatus.NOT_ACCEPTABLE);
	}

	public static ResponseEntity<CustomErrorType> erroCodigoAcessoIncorreto() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEstabelecimento.CODIGO_ACESSO_INCORRETO),
				HttpStatus.NOT_ACCEPTABLE);
	}
}
