package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborEstaDisponivelException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;

public interface SaborService {

    public SaborRequestDTO criarSabor(Long idEstabelecimento, SaborRequestDTO saborRequestDTO)
            throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException;

    public SaborResponseDTO getSaborById(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public SaborRequestDTO atualizarSabor(Long idEstabelecimento, Long idSabor, SaborRequestDTO saborRequestDTO)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public void removerSaborCadastrado(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public List<SaborResponseDTO> listarSabores();

    public SaborDTO getSaborById(Long id) throws SaborNotFoundException;
    
    public SaborDTO editarDisponibilidadeSabor(long idEstabelecimento, long idSabor, boolean estaDisponivel)  
    		throws SaborNotFoundException, EstabelecimentoNotFoundException;
    
	public SaborDTO demonstrarInteresseEmSabor(long idCliente, long idSabor) 
			throws ClienteNotFoundException, SaborNotFoundException, SaborEstaDisponivelException;

}