package br.com.ufcg.ccc.psoft.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tipo;

	private double valor;

	public Pagamento() {
	}

	public Pagamento(String tipo, double valor) {
		this.tipo = tipo;
		this.valor = valor;
	}

}
