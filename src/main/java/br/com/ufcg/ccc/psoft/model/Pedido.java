package br.com.ufcg.ccc.psoft.model;


import java.util.List;
import javax.persistence.*;
import br.com.ufcg.ccc.psoft.model.Enum.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@NoArgsConstructor
@Entity
public class Pedido {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Cliente.class,cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Cliente cliente;

	@ManyToOne(targetEntity = Estabelecimento.class)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Estabelecimento estabelecimento;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List <ItemDePedido> itensEscolhidos;
	
	@OneToOne(targetEntity = Pagamento.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Pagamento pagamento;

	private String enderecoEntrega;

	private Double valorTotal;

	private String statusPedido;

	@ManyToOne(targetEntity = Entregador.class)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Entregador entregador;

	public Pedido(Cliente cliente, Estabelecimento estabelecimento, List<ItemDePedido> itensEscolhidos, Pagamento pagamento, String enderecoEntrega, Double valorTotal) {
		this.cliente = cliente;
		this.estabelecimento = estabelecimento;
		this.itensEscolhidos = itensEscolhidos;
		this.pagamento = pagamento;
		this.enderecoEntrega = enderecoEntrega;
		this.valorTotal = valorTotal;
		this.statusPedido = "Pedido recebido";
	}

	public void notifyCliente(){
		System.out.println("Sr(a) " + cliente.getNomeCompleto() + " seu pedido está em rota." + "\n"
			+ "Informações do Entregador:" + "\n"
			+ "Nome: " + entregador.getNomeCompleto() + "\n"
			+ entregador.getVeiculo().toString());
	}

	public void notifyEstabelecimento() {
		System.out.println("O Cliente " + cliente.getNomeCompleto() + " confirmou a entrega do seu pedido" +
				"." + "\n"
				+ "Estabelecimento:" + "\n"
				+ "ID: " + estabelecimento.getId());
	}

	public void setItensEscolhidos(List<ItemDePedido> itensDePedido) {
		if (this.itensEscolhidos == null) {
		    this.itensEscolhidos = itensDePedido;
		  } else {
		    this.itensEscolhidos.clear();
		   this.itensEscolhidos.addAll(itensDePedido);
		  }
	}
}