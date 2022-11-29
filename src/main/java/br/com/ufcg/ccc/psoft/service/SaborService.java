package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;

public interface SaborService {

	public SaborDTO criarSabor(long idEstabelecimento, SaborDTO saborDTO) throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException;
	
	public SaborDTO getSaborById(Long id) throws SaborNotFoundException;
	
	public SaborDTO atualizarSabor(Long id, SaborDTO saborDTO) throws SaborNotFoundException;
	
	public void removerSaborCadastrado(Long id) throws SaborNotFoundException;
	
	
	
}
