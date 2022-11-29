package br.com.ufcg.ccc.psoft.dto;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class SaborDTO {

	private Long id;

	private String nomeSabor;

	private String tipo; 
	
	private Double valorMedio;

	private Double valorGrande;

	public Long getId() {
		return id;
	}

	public String getNomeSabor() {
		return nomeSabor;
	}

	public String getTipo() {
		return tipo;
	}

	public Double getValorMedio() {
		return valorMedio;
	}

	public Double getValorGrande() {
		return valorGrande;
	}
}
