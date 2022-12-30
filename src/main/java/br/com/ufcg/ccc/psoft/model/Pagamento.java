package br.com.ufcg.ccc.psoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPagamento;

    private double desconto;

    public Pagamento(String tipoPagamento, double desconto){
        this.tipoPagamento = tipoPagamento;
        this.desconto = desconto;
    }
    public abstract double calculaDesconto(Double valor);


}
