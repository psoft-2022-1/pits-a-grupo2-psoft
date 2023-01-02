package br.com.ufcg.ccc.psoft.dto.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDePedidoResponseDTO {

	private List<String> nomeSabores;
	
	private String tamanho; //medio ou grande
	
	private double valor;


	
}
