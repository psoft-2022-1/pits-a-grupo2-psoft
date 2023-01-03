package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPedido {

	static final String PEDIDO_NAO_ENCONTRADO = "Pedido com id %s não foi encontrado.";

	static final String PEDIDO_DE_CLIENTE_NAO_ENCONTRADO = "O cliente de id %s não tem pedido com id %s";

	static final String CLIENTE_SEM_PEDIDOS = "O cliente de id %s não tem pedidos";

	static final String CLIENTE_SEM_PEDIDOS_STATUS = "O cliente de id %s não tem pedidos com Status %s";

	static final String PEDIDO_NAO_PERTENCE_A_ESSE_CLIENTE = "Pedido com id %s não pertence a cliente %s.";
	
	static final String PEDIDO_JA_ESTA_PRONTO = "Pedido com id %s já está pronto.";
	
	static final String PEDIDO_COM_STATUS_INCORRETO_PARA_MUDANCA = "Pedido com id %s nao pode ter status alterado. O status atual nao se refere ao indicado para mudanca";
	
	static final String QUANTIDADE_SABORES_NO_PEDIDO_INVALIDO = "Quantidade de sabores em algum dos itens do pedido é invalida";
	
	public static ResponseEntity<CustomErrorType> erroPedidoNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroPedidoClienteNaoEncontrado(long idCliente, long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_DE_CLIENTE_NAO_ENCONTRADO, idCliente, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroClientesSemPedidos(long idCliente) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.CLIENTE_SEM_PEDIDOS, idCliente)),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<CustomErrorType> erroClientesSemPedidosStatus(long idCliente, String status) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.CLIENTE_SEM_PEDIDOS_STATUS, idCliente, status)),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<CustomErrorType> pedidoNaoPertenceAEsseCliente(long idPedido, long IdCliente){
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_PERTENCE_A_ESSE_CLIENTE, idPedido, IdCliente)),
				HttpStatus.NOT_FOUND);

	}

	public static ResponseEntity<?> pedidoJaEstaPronto(long idPedido) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_JA_ESTA_PRONTO, idPedido)),
				HttpStatus.NOT_FOUND);

	}

	public static ResponseEntity<?> QuantidadeSaboresDoPedidoInvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(QUANTIDADE_SABORES_NO_PEDIDO_INVALIDO),
				HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<?> pedidoComStatusIncorretoParaMudanca(long idPedido) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPedido.PEDIDO_COM_STATUS_INCORRETO_PARA_MUDANCA, idPedido)),
				HttpStatus.BAD_REQUEST);
		
	}
}
