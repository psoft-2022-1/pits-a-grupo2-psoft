package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;

public interface ClienteService {

	 public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException;

	 public void removerClienteCadastrado(Long id) throws ClienteNotFoundException;

	 public List<ClienteDTO> listarClientes();

	 public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException;

	 public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException;
}