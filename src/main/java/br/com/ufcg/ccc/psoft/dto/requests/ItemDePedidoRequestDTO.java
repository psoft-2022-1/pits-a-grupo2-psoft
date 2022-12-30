package br.com.ufcg.ccc.psoft.dto.requests;

import java.util.List;

import lombok.Data;

import br.com.ufcg.ccc.psoft.model.Sabor;
@Data
public class ItemDePedidoRequestDTO {

	private List<Sabor> sabores;
	
	private String tamanho; //medio ou grande
	
	private double valor;

}
