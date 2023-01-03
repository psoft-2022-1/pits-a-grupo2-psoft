package br.com.ufcg.ccc.psoft.service;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.ClienteResponseDTO;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public ClienteRequestDTO getClienteById(Long idCliente) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(idCliente);
		return modelMapper.map(cliente, ClienteRequestDTO.class);
	}

	@Override
	public Cliente getClienteId(Long idCliente) throws ClienteNotFoundException {
		return clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ClienteNotFoundException());
	}
	@Override
	public void removerClienteCadastrado(Long idCliente) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(idCliente);
		this.clienteRepository.delete(cliente);
	}

	@Override
	public List<ClienteResponseDTO> listarClientes() {
		List<ClienteResponseDTO> clientesDTO = this.clienteRepository.findAll()
				.stream()
				.map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
				.collect(Collectors.toList());

		return clientesDTO;
	}

	@Override
	public ClienteResponseDTO criarCliente(ClienteRequestDTO clienteRequestDTO) throws InvalidCodigoAcessoException {
		if(clienteRequestDTO.getCodAcesso().length() != 6){
			throw new InvalidCodigoAcessoException();
		}
		Cliente cliente = new Cliente(clienteRequestDTO.getCodAcesso(), clienteRequestDTO.getNomeCompleto(),
				clienteRequestDTO.getEnderecoPrincipal());

		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteResponseDTO.class);
	}

	@Override
	public ClienteResponseDTO atualizarCliente(Long idCliente, ClienteRequestDTO clienteRequestDTO) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(idCliente);

		cliente.setEnderecoPrincipal(clienteRequestDTO.getEnderecoPrincipal());
		cliente.setNomeCompleto(clienteRequestDTO.getNomeCompleto());
		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteResponseDTO.class);
	}

	@Override
	public Cliente checkCodAcesso(Long idCliente, String codCliente) throws IncorretCodigoAcessoException, ClienteNotFoundException {
		if (!getClienteId(idCliente).getCodAcesso().equals(codCliente)) {
			throw new IncorretCodigoAcessoException();
		}
		return getClienteId(idCliente);
	}

}