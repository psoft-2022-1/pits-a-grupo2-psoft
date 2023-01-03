package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cardapio;

public interface CardapioService {

	public CardapioResponseDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor)
			throws CardapioNotFoundException;

	public CardapioResponseDTO getCardapio(Cardapio cardapio);
}