package br.com.ufcg.ccc.psoft.service;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.SenhaInvalidaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

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
    public ClienteDTO removeClienteCadastrado(Long id, ClienteDTO clienteDTO) throws IncorretCodigoAcessoException, ClienteNotFoundException {

        Cliente cliente = getClienteId(id);

        if (cliente.getCodAcesso().equals(clienteDTO.getCodigoAcesso()))
            throw new IncorretCodigoAcessoException();

        this.clienteRepository.delete(cliente);

        return modelMapper.map(cliente, ClienteDTO.class);
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
    public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws SenhaInvalidaException {

        if (clienteDTO.getCodigoAcesso().length() != 6)
            throw new SenhaInvalidaException();

        Cliente cliente = new Cliente(clienteDTO);

        this.clienteRepository.save(cliente);

        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException, IncorretCodigoAcessoException, SenhaInvalidaException {
        Cliente cliente = getClienteId(id);

        if (!clienteDTO.getCodigoAcesso().equals(cliente.getCodAcesso()))
            throw new IncorretCodigoAcessoException();
        else if (clienteDTO.getNovoCodigoAcesso().length() != 6)
            throw new SenhaInvalidaException();

        cliente.setEnderecoPrincipal(clienteDTO.getEnderecoPrincipal());
        cliente.setNomeCompleto(clienteDTO.getNomeCompleto());
        cliente.setCodAcesso(clienteDTO.getNovoCodigoAcesso());
        this.clienteRepository.save(cliente);

        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public Cliente checkCodAcesso(Long id, String codCliente) throws IncorretCodigoAcessoException, ClienteNotFoundException {
        if (!getClienteId(id).getCodAcesso().equals(codCliente)) {
            throw new IncorretCodigoAcessoException();
        }
        return getClienteId(id);
    }
}