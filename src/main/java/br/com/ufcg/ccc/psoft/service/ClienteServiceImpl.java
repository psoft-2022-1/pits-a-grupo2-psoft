package br.com.ufcg.ccc.psoft.service;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
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
	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	private Cliente getClienteId(Long id) throws ClienteNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException());
	}
	@Override
	public void removeClienteCadastrado(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		this.clienteRepository.delete(cliente);
	}

	@Override
	public List<ClienteDTO> listaClientes() {
		List<ClienteDTO> clientesDTO = this.clienteRepository.findAll()
				.stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());

		return clientesDTO;
	}

	@Override
	public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException {
		if(isClienteCadastrado(clienteDTO.getId())){
			throw new ClienteAlreadyCreatedException();
		}
		Cliente cliente = new Cliente(clienteDTO.getCodAcesso(),clienteDTO.getNomeCompleto(),
				clienteDTO.getEnderecoPrincipal());

		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteDTO.class);
	}

	private boolean isClienteCadastrado(Long id) {
		try {
			getClienteById(id);
			return true;
		} catch (ClienteNotFoundException e) {
			return false;
		}
	}

	@Override
	public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);

		cliente.setEnderecoPrincipal(clienteDTO.getEnderecoPrincipal());
		cliente.setNomeCompleto(clienteDTO.getNomeCompleto());
		this.clienteRepository.save(cliente);

		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	public boolean checkCodAcesso(ClienteDTO clienteDTO, String codCliente) throws IncorretCodigoAcessoException {
		if (!clienteDTO.getCodAcesso().equals(codCliente)) {
			throw new IncorretCodigoAcessoException();
		}
		return true;
	}

	
}

