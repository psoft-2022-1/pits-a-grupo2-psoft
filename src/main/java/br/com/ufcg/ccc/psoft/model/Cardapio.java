package br.com.ufcg.ccc.psoft.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Cardapio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Sabor> sabores;

	public Cardapio() {
	}

	public Cardapio(List<Sabor> sabor) {
		this.sabores = new ArrayList<>();
	}

	public void adicionarSabor(Sabor sabor) {
		this.sabores.add(sabor);
	}

	public List<Sabor> getSabores() {
		return this.sabores;
	}

	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}
}