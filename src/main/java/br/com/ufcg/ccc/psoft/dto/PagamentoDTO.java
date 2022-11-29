package br.com.ufcg.ccc.psoft.dto;

import lombok.Data;

@Data
public class PagamentoDTO {

	private Long id;

	private String tipo;

	private double valor;

}
