package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.CardapioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EstabelecimentoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;


public interface EstabelecimentoService {

    EstabelecimentoRequestDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException;

    boolean checkCodAcesso(EstabelecimentoRequestDTO estabelecimentoDTO, String codEstabelecimento) throws IncorretCodigoAcessoException;

    public CardapioResponseDTO consultaCarcapioPorSabor(Long idEstabeleicmento, String tipoSabor) throws EstabelecimentoNotFoundException, CardapioNotFoundException;

    public EstabelecimentoRequestDTO criarEstabelecimento(EstabelecimentoRequestDTO codigoAcesso);

    public EstabelecimentoRequestDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoRequestDTO codigoAcesso) throws EstabelecimentoNotFoundException;


    public CardapioRequestDTO getCardapio(Long idEstabelecimento) throws EstabelecimentoNotFoundException;
}
