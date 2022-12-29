package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.senhaInvalidaException;
import br.com.ufcg.ccc.psoft.model.Cliente;

public interface ClienteService {

	 public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException;

	 public ClienteDTO removeClienteCadastrado(Long id, ClienteDTO clienteDTO) throws IncorretCodigoAcessoException, ClienteNotFoundException;

	 public List<ClienteDTO> listaClientes();

	 public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException, senhaInvalidaException;

	 public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException, IncorretCodigoAcessoException, senhaInvalidaException;

	public Cliente checkCodAcesso(Long id, String codCliente) throws IncorretCodigoAcessoException, ClienteNotFoundException;

}