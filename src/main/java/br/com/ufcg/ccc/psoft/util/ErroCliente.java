package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCliente {

	static final String CLIENTE_NAO_ENCONTRADO = "Cliente com id %s não foi encontrado.";
	
	public static ResponseEntity<CustomErrorType> erroProdutoNaoEncontradoNoCarrinho(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCliente.CLIENTE_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
}
