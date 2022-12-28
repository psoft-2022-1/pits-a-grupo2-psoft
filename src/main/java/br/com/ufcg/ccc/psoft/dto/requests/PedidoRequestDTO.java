package br.com.ufcg.ccc.psoft.dto.requests;

import java.util.List;

import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import lombok.Data;

@Data
public class PedidoRequestDTO {

	private Long id;
	
   	private String codCliente;

	private List <ItemDePedido> itensEscolhidos;
   	
    private String tipoPagamento;

	private String enderecoEntrega;
	
}
