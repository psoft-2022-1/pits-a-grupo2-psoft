package br.com.ufcg.ccc.psoft.dto.responses;

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioResponseDTO {

	private Long id;

    private List <SaborResponseDTO> sabores;
}
