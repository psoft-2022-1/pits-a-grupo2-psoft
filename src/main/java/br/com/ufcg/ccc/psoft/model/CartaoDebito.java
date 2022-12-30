package br.com.ufcg.ccc.psoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
public class CartaoDebito extends Pagamento {

    public CartaoDebito (){
        super("Cartão de débito", 0.025);
    }

    @Override
    public double calculaDesconto(Double valor) {
        return valor * this.getDesconto();
    }
}
