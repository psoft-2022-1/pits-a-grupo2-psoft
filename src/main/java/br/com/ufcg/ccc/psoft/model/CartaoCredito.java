package br.com.ufcg.ccc.psoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
public class CartaoCredito extends Pagamento {

	public CartaoCredito(){
		super("Cartão de crédito", 1);
	}

	@Override
	public double calculaDesconto(Double valor) {
        return valor * this.getPorcentagemRestante();
    }

}
