package br.com.ufcg.ccc.psoft.dto;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;

    private String codAcesso;

    private String nomeCompleto;

    private String enderecoPrincipal;

}
