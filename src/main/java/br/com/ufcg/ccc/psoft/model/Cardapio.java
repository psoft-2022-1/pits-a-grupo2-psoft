package br.com.ufcg.ccc.psoft.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Entity
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
    private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Getter
	private List <Sabor> sabor;

	public Cardapio(){}

	public Cardapio(List<Sabor> sabor) {
		this.sabor = new ArrayList<>();
	}
}