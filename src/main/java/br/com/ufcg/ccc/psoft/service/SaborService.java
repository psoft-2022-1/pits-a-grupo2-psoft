package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;

public interface SaborService {

	public SaborDTO criarSabor( SaborDTO saborDTO) throws SaborAlreadyCreatedException;
	
	public SaborDTO getSaborById(Long id) throws SaborNotFoundException;
	
}
