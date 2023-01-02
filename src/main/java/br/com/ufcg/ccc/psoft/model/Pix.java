package br.com.ufcg.ccc.psoft.model;

import lombok.Data;
import javax.persistence.Entity;

@Data
@Entity
public class Pix extends Pagamento{

    public Pix(){
        super("Pix", 0.95);
    }

    @Override
    public double calculaDesconto(Double valor) {
        return valor * this.getPorcentagemRestante();
    }

}
