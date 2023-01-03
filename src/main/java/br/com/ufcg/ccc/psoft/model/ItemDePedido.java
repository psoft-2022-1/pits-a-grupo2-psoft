package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
@Data
@Entity
@NoArgsConstructor
public class ItemDePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(cascade = CascadeType.PERSIST) 
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Sabor> sabores;

	private String tamanho; // medio ou grande

	private double valor;

	public ItemDePedido(List<Sabor> sabores, String tamanho, double valor) {
		this.sabores = sabores;
		this.tamanho = tamanho;
		this.valor = valor;
	}

}
