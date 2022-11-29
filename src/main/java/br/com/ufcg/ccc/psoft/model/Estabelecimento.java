package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Setter
    private String codigoAcesso;

    @OneToOne(targetEntity = Cardapio.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter
	@Setter
    private Cardapio cardapio;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter
    private List<Funcionario> funcionarios;

    public Estabelecimento(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
        this.cardapio = new Cardapio();
        this.funcionarios = new ArrayList<>();
    }
}
