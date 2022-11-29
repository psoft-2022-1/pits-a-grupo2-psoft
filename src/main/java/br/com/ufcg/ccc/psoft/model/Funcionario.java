package br.com.ufcg.ccc.psoft.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeCompleto;

	private String codigoAcesso;

	public Funcionario() {
	}

	public Funcionario(String nomeCompleto, String codigoAcesso) {
		this.nomeCompleto = nomeCompleto;
		this.codigoAcesso = codigoAcesso;
	}
}
