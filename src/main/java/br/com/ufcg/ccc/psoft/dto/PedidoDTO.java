package br.com.ufcg.ccc.psoft.dto;

import java.util.List;

import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import br.com.ufcg.ccc.psoft.model.Sabor;
@Data
public class PedidoDTO {

	private Long id;
	
   	private Cliente cliente;

	private List <ItemDePedido> itensEscolhidos;
   	
    private Pagamento pagamento;

	private String enderecoEntrega;

	private Double valorTotal;

	public double calculaTotal(){
		double total = 0;
		for(ItemDePedido item : this.itensEscolhidos){
			total += item.getValor();
		}
		return total;
	}

	public double getValorTotal() {
		return calculaTotal();
	}
	
}
