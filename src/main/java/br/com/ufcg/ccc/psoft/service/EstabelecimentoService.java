package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.CardapioDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;


public interface EstabelecimentoService {

    EstabelecimentoDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException;

    boolean checkCodAcesso(EstabelecimentoDTO estabelecimentoDTO, String codEstabelecimento) throws IncorretCodigoAcessoException;

    public CardapioDTO consultaCarcapioPorSabor(Long idEstabeleicmento, String tipoSabor) throws EstabelecimentoNotFoundException, CardapioNotFoundException;

    public EstabelecimentoDTO criarEstabelecimento(EstabelecimentoDTO codigoAcesso);

    public EstabelecimentoDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoDTO codigoAcesso) throws EstabelecimentoNotFoundException;


    public CardapioDTO getCardapio(Long idEstabelecimento) throws EstabelecimentoNotFoundException;
}
