package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;

import java.util.List;

public interface EntregadorService {

    public EntregadorRequestDTO criaEntregador(EntregadorRequestDTO entregadorRequestDTO) throws EntregadorAlreadyCreatedException;

    public List<EntregadorRequestDTO> listarEntregadores();

    public void removerEntregadorCadastrado(Long id) throws EntregadorNotFoundException;

    public EntregadorRequestDTO getEntregadorById(Long id) throws EntregadorNotFoundException;

    public EntregadorRequestDTO atualizaEntregador(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException;

}