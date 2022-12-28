package br.com.ufcg.ccc.psoft.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioRequestDTO {

	private Long id;

    private List <SaborRequestDTO> sabores;
}
