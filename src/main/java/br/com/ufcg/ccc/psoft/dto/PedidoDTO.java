package br.com.ufcg.ccc.psoft.dto;

import java.util.List;

import br.com.ufcg.ccc.psoft.model.Enum.StatusPedido;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import lombok.Data;

@Data
public class PedidoDTO {

	private Long id;
	
   	private String codCliente;

	private List <ItemDePedido> itensEscolhidos;

	private StatusPedido statusPedido;
   	
    private String tipoPagamento;

	private String enderecoEntrega;
	
}
