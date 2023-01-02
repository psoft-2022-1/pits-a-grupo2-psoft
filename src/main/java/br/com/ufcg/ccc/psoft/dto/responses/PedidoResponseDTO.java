package br.com.ufcg.ccc.psoft.dto.responses;

import java.util.List;

import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

	private Long id;

	private List <ItemDePedidoResponseDTO> itensEscolhidos;
   	
    private String pagamento;

	private String enderecoEntrega;
	
	private double valorTotal;
	
	private String statusPedido;
	
}
