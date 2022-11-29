package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.CardapioDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;

public interface EstabelecimentoService {

    public CardapioDTO consultaCarcapioPorSabor(Long idEstabeleicmento, String tipoSabor) throws EstabelecimentoNotFoundException, CardapioNotFoundException;

    public EstabelecimentoDTO criarEstabelecimento(EstabelecimentoDTO codigoAcesso);

    public EstabelecimentoDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoDTO codigoAcesso) throws EstabelecimentoNotFoundException;

}
