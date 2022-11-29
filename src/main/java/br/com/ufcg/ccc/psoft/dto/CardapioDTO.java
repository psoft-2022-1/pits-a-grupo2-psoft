package br.com.ufcg.ccc.psoft.dto;

import java.util.List;
import br.com.ufcg.ccc.psoft.model.Sabor;
import lombok.Data;

@Data
public class CardapioDTO {

	private Long id;

    private List <Sabor> sabores;

    public Long getId() {
        return id;
    }

    public List<Sabor> getSabores() {
        return sabores;
    }
}
