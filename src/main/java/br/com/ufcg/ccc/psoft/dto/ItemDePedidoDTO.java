package br.com.ufcg.ccc.psoft.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.ufcg.ccc.psoft.model.Sabor;

public class ItemDePedidoDTO {

	private Long id;

	private List<Sabor> sabores;
	
	private String tamanho; //medio ou grande
	
	private double valor;

	public Long getId() {
		return id;
	}

	public List<Sabor> getSabores() {
		return sabores;
	}

	public String getTamanho() {
		return tamanho;
	}

	public double getValor() {
		return valor;
	}
}
