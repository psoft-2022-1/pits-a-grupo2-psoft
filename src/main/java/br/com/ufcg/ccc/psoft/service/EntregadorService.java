package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNaoAprovadoException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;

import java.util.List;

public interface EntregadorService {

    public EntregadorRequestDTO criaEntregador(EntregadorRequestDTO entregadorRequestDTO) throws EntregadorAlreadyCreatedException;

    public List<EntregadorResponseDTO> listarEntregadores();

    public void removerEntregadorCadastrado(Long id) throws EntregadorNotFoundException;

    public EntregadorResponseDTO getEntregadorById(Long id) throws EntregadorNotFoundException;

    public EntregadorRequestDTO atualizaEntregador(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException;

    public EntregadorResponseDTO atualizaStatusDisponibilidade(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException, EntregadorNaoAprovadoException, IncorretCodigoAcessoException;

}