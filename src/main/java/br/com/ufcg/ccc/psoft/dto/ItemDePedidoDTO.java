package br.com.ufcg.ccc.psoft.dto;

import java.util.List;

import lombok.Data;

import br.com.ufcg.ccc.psoft.model.Sabor;
@Data
public class ItemDePedidoDTO {

	private Long id;

	private List<Sabor> sabores;
	
	private String tamanho; //medio ou grande
	
	private double valor;

}
