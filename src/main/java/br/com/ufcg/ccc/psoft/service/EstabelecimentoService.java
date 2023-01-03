package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EstabelecimentoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EstabelecimentoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;

public interface EstabelecimentoService {

	public EstabelecimentoRequestDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException;

	public Estabelecimento getEstabelecimentoById(Long idEstabelecimento) throws EstabelecimentoNotFoundException;

	public boolean checkCodAcesso(EstabelecimentoRequestDTO estabelecimentoDTO, String codEstabelecimento)
			throws IncorretCodigoAcessoException;

	public CardapioResponseDTO consultaCarcapioPorSabor(Long idEstabeleicmento, String tipoSabor)
			throws EstabelecimentoNotFoundException, CardapioNotFoundException;

	public EstabelecimentoResponseDTO criarEstabelecimento(EstabelecimentoRequestDTO codigoAcesso)
			throws InvalidCodigoAcessoException;

	public EstabelecimentoResponseDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoRequestDTO codigoAcesso) throws EstabelecimentoNotFoundException;

    public CardapioResponseDTO getCardapio(Long idEstabelecimento) throws EstabelecimentoNotFoundException;
}
