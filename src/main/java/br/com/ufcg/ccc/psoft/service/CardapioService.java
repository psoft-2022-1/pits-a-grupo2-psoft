/**
 * 
 */
package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.CardapioDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;

/**
 * @author aline
 *
 */
public interface CardapioService {

    public CardapioDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor) throws CardapioNotFoundException;
}
