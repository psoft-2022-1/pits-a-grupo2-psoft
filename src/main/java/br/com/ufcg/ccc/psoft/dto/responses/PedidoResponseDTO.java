package br.com.ufcg.ccc.psoft.dto.responses;

import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import lombok.Data;

import java.util.List;

@Data
public class PedidoResponseDTO {

	private Long id;

	private List <ItemDePedido> itensEscolhidos;
   	
    private String tipoPagamento;

	private String enderecoEntrega;
	
}
