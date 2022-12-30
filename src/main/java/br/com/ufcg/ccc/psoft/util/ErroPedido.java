package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPedido {

	static final String PEDIDO_NAO_ENCONTRADO = "Pedido com id %s não foi encontrado.";

	static final String PEDIDO_DE_CLIENTE_NAO_ENCONTRADO = "O cliente %s não tem pedido com id %s";

	static final String CLIENTE_SEM_PEDIDOS = "O cliente %s não tem pedidos";

	static final String CLIENTE_SEM_PEDIDOS_STATUS = "O cliente %s não tem pedidos com Status %s";

	public static ResponseEntity<CustomErrorType> erroPedidoNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroPedidoClienteNaoEncontrado(String nome, long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_DE_CLIENTE_NAO_ENCONTRADO, nome, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroClientesSemPedidos(String nome) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.CLIENTE_SEM_PEDIDOS, nome)),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<CustomErrorType> erroClientesSemPedidosStatus(String nome, String status) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.CLIENTE_SEM_PEDIDOS_STATUS, nome, status)),
				HttpStatus.NO_CONTENT);
	}
}
