package br.com.ufcg.ccc.psoft.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
    private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Getter
	@Setter
	private List <Sabor> sabores;

	public Cardapio() {
		this.sabores = new ArrayList<>();
	}
	public void adicionarSabor(Sabor sabor) {
		this.sabores.add(sabor);
	}
}