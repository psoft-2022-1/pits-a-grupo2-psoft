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

    private double porcentagemRestante;

    public Pagamento(String tipoPagamento, double porcentagemRestante){
        this.tipoPagamento = tipoPagamento;
        this.porcentagemRestante = porcentagemRestante;
    }
    
    public abstract double calculaDesconto(Double valor);

	
}
