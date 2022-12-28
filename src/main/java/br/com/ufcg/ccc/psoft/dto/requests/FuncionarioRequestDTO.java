package br.com.ufcg.ccc.psoft.dto.requests;

import lombok.Data;

@Data
public class FuncionarioRequestDTO {

	private Long id;

	private String nomeCompleto;

	private String codigoAcesso;

}