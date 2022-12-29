package br.com.ufcg.ccc.psoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
public class Pix extends Pagamento{

    public Pix(){
        super("Pix", 0.05);
    }

    @Override
    public double calculaDesconto(Double valor) {
        return valor * this.getDesconto();
    }

}
