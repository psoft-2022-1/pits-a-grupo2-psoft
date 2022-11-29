package br.com.ufcg.ccc.psoft.dto;

import java.util.List;

import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import br.com.ufcg.ccc.psoft.model.Sabor;

public class PedidoDTO {

	private Long id;
	
   	private Cliente cliente;

	private List <ItemDePedido> itensEscolhidos;
   	
    private Pagamento pagamento;

	private String enderecoEntrega;

	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemDePedido> getItensEscolhidos() {
		return itensEscolhidos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}
}
