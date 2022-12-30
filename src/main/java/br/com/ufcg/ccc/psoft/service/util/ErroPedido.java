package br.com.ufcg.ccc.psoft.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPedido {

	static final String PEDIDO_NAO_ENCONTRADO = "Pedido com id %s não foi encontrado.";
	static final String PEDIDO_NAO_PERTENCE_A_ESSE_CLIENTE = "Pedido com id %s não pertence a cliente %s.";
	static final String PEDIDO_JA_ESTA_PRONTO = "Pedido com id %s já está pronto.";

	public static ResponseEntity<CustomErrorType> erroPedidoNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> pedidoNaoPertenceAEsseCliente(long idPedido, long IdCliente){
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_PERTENCE_A_ESSE_CLIENTE, idPedido, IdCliente)),
				HttpStatus.NOT_FOUND);
		
	}

	public static ResponseEntity<?> pedidoJaEstaPronto(long idPedido) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_JA_ESTA_PRONTO, idPedido)),
				HttpStatus.NOT_FOUND);
		
	}
}
