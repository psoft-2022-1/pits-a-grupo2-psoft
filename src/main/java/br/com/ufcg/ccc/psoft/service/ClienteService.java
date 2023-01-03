package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.ClienteResponseDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
import br.com.ufcg.ccc.psoft.model.Cliente;

public interface ClienteService {

	public ClienteRequestDTO getClienteById(Long id) throws ClienteNotFoundException;

	public void removerClienteCadastrado(Long id) throws ClienteNotFoundException;

	public List<ClienteResponseDTO> listarClientes();

	public ClienteResponseDTO criarCliente(ClienteRequestDTO clienteRequestDTO)
			throws ClienteAlreadyCreatedException, InvalidCodigoAcessoException;

	public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO)
			throws ClienteNotFoundException;

	public Cliente checkCodAcesso(Long id, String codCliente)
			throws IncorretCodigoAcessoException, ClienteNotFoundException;

	public Cliente getClienteId(Long id) throws ClienteNotFoundException;
}