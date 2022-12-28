package br.com.ufcg.ccc.psoft.dto.requests;

import lombok.Data;

@Data
public class PagamentoRequestDTO {

	private Long id;

	private String tipo;

	private double valor;

}
