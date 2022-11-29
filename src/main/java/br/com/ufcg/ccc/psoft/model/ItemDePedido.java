package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Data
@Entity
public class ItemDePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Sabor> sabores;

	private String tamanho; // medio ou grande

	private double valor;

	public ItemDePedido() {
	}


	// ItemDePedido com um sabor apenas
	public ItemDePedido(Sabor sabor, String tamanho, double valor) {
		this.sabores = new ArrayList<>();
		this.tamanho = tamanho;
		this.valor = valor;
	}

	// ItemDePedido com 2 sabores
	public ItemDePedido(Sabor sabor1, Sabor sabor2, String tamanho, double valor) {
		this.sabores = new ArrayList<>();
		this.tamanho = tamanho;
		this.valor = valor;
	}

}
