/**
 * 
 */
package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.CardapioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;

/**
 * @author aline
 *
 */
public interface CardapioService {

    public CardapioResponseDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor) throws CardapioNotFoundException;
}
