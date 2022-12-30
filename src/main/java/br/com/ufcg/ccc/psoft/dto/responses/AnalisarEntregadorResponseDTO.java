package br.com.ufcg.ccc.psoft.dto.responses;

import lombok.Data;

@Data
public class AnalisarEntregadorResponseDTO {

    private Long idEstabelecimento;

    private Long idFuncionario;

    private String codFuncionario;

    private Long idEntregador;

    private String status;
}
