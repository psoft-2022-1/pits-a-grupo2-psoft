package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCardapio {

	static final String CARDAPIO_NAO_ENCONTRADO = "Funcionario com id %s não foi encontrado.";
	
	public static ResponseEntity<CustomErrorType> erroProdutoNaoEncontradoNoCarrinho(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCardapio.CARDAPIO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
}
