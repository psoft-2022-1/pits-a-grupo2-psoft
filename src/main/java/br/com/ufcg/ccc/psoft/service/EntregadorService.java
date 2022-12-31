package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;

import java.util.List;

public interface EntregadorService {

    public EntregadorResponseDTO criaEntregador(EntregadorRequestDTO entregadorRequestDTO) throws EntregadorAlreadyCreatedException, InvalidCodigoAcessoException;

    public List<EntregadorResponseDTO> listarEntregadores();

    public void removerEntregadorCadastrado(Long id) throws EntregadorNotFoundException;

    public EntregadorResponseDTO getEntregadorById(Long id) throws EntregadorNotFoundException;

    public EntregadorRequestDTO atualizaEntregador(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException;

    public EntregadorResponseDTO atualizaStatusDisponibilidade(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException, EntregadorNaoAprovadoException, IncorretCodigoAcessoException;
}