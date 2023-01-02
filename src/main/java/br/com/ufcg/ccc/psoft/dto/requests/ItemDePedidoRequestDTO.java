package br.com.ufcg.ccc.psoft.dto.requests;

import java.util.List;

import lombok.Data;

@Data
public class ItemDePedidoRequestDTO {

	private List<SaborCreateRequestDTO> sabores;
	
	private String tamanho; //medio ou grande

}
