package br.com.ufcg.ccc.psoft.dto;

public class ClienteDTO {

    private Long id;

    private String codAcesso;

    private String nomeCompleto;

    private String enderecoPrincipal;

    public Long getId() {
        return id;
    }

    public String getCodAcesso() {
        return codAcesso;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEnderecoPrincipal() {
        return enderecoPrincipal;
    }
}
