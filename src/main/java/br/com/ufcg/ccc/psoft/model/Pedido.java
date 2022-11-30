package br.com.ufcg.ccc.psoft.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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

	@ManyToOne(targetEntity = Cliente.class)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List <ItemDePedido> itensEscolhidos;
	@OneToOne(targetEntity = Pagamento.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Pagamento pagamento;

	private String enderecoEntrega;

	public Pedido(Cliente cliente, List<ItemDePedido> itensEscolhidos, Pagamento pagamento, String enderecoEntrega) {
		this.cliente = cliente;
		this.itensEscolhidos = new ArrayList<>();
		this.pagamento = pagamento;
		this.enderecoEntrega = enderecoEntrega;
	}
}