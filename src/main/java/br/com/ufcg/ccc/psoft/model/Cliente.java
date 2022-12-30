package br.com.ufcg.ccc.psoft.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codAcesso;

    private String nomeCompleto;

    @NotNull
    private String enderecoPrincipal;

    public Cliente(String codAcesso, String nomeCompleto, String enderecoPrincipal){
        this.codAcesso = codAcesso;
        this.nomeCompleto = nomeCompleto;
        this.enderecoPrincipal = enderecoPrincipal;
    }

}