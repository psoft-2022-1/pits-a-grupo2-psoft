package br.com.ufcg.ccc.psoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String codAcesso;

    private String nomeCompleto;

    private String enderecoPrincipal;

}