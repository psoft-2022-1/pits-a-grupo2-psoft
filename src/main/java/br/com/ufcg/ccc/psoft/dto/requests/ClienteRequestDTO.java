package br.com.ufcg.ccc.psoft.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    private Long id;

    private String codAcesso;

    private String nomeCompleto;

    private String enderecoPrincipal;

}
