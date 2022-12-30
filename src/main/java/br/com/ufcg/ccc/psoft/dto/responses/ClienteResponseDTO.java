package br.com.ufcg.ccc.psoft.dto.responses;

import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Long id;

    private String nomeCompleto;

    private String enderecoPrincipal;

}
