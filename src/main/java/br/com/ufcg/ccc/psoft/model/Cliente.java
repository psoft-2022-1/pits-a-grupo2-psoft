package br.com.ufcg.ccc.psoft.model;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codAcesso;

    private String nomeCompleto;

    @NotNull
    private String enderecoPrincipal;

    public Cliente(){}

    public Cliente(ClienteDTO clienteDTO){
        this.codAcesso = clienteDTO.getCodigoAcesso();
        this.nomeCompleto = clienteDTO.getNomeCompleto();
        this.enderecoPrincipal = clienteDTO.getEnderecoPrincipal();
    }
}