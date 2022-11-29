package br.com.ufcg.ccc.psoft.dto;

import lombok.Data;

@Data
public class AnalisarEntregadorRequestDTO {

    private Long idEstabelecimento;

    private String codEstabelecimento;

    private Long idFuncionario;

    private String codFuncionario;

    private Long idEntregador;

    private String status;
}
