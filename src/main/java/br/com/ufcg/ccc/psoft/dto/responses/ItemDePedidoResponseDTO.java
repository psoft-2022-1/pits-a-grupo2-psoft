package br.com.ufcg.ccc.psoft.dto.responses;

import br.com.ufcg.ccc.psoft.model.Sabor;
import lombok.Data;

import java.util.List;

@Data
public class ItemDePedidoResponseDTO {

	private Long id;

	private List<Sabor> sabores;
	
	private String tamanho; //medio ou grande
	
	private double valor;

}
