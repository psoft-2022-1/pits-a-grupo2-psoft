package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroItemDePedido {

	static final String ITEMDEPEDIDO_NAO_ENCONTRADO = "Item de pedido com id %s não foi encontrado.";
	
	public static ResponseEntity<CustomErrorType> erroProdutoNaoEncontradoNoCarrinho(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroItemDePedido.ITEMDEPEDIDO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
}
