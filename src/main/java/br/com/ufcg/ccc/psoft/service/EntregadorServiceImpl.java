package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNaoAprovadoException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.model.Entregador;
import br.com.ufcg.ccc.psoft.model.Veiculo;
import br.com.ufcg.ccc.psoft.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    public ModelMapper modelMapper;

    public EntregadorRequestDTO criaEntregador(EntregadorRequestDTO entregadorRequestDTO) throws EntregadorAlreadyCreatedException {

        if(isEntregadorCadastrado(entregadorRequestDTO.getNomeCompleto())) {
            throw new EntregadorAlreadyCreatedException();
        }

        Veiculo veiculo = salvarVeiculoEntregador(entregadorRequestDTO.getVeiculo());
        Entregador entregador = new Entregador(entregadorRequestDTO.getNomeCompleto(), veiculo, entregadorRequestDTO.getStatusEstabelecimento(), entregadorRequestDTO.getCodigoAcesso());
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorRequestDTO.class);
    }

    public List<EntregadorResponseDTO> listarEntregadores() {
        List<EntregadorResponseDTO> entregadores = entregadorRepository.findAll()
                .stream()
                .map(entregador -> modelMapper.map(entregador, EntregadorResponseDTO.class))
                .collect(Collectors.toList());
        return entregadores;
    }

    public void removerEntregadorCadastrado(Long id) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        entregadorRepository.delete(entregador);
    }

    public EntregadorRequestDTO atualizaEntregador(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException {

        Entregador entregador = getEntregadorId(id);

        entregador.setNomeCompleto(entregadorRequestDTO.getNomeCompleto());
        entregador.setStatusEstabelecimento(entregadorRequestDTO.getStatusEstabelecimento());
        entregador.setCodigoAcesso(entregadorRequestDTO.getCodigoAcesso());
        entregador.setVeiculo(entregadorRequestDTO.getVeiculo());
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorRequestDTO.class);
    }

    @Override
    public EntregadorResponseDTO atualizaStatusDisponibilidade(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException, EntregadorNaoAprovadoException, IncorretCodigoAcessoException {

        Entregador entregador = this.entregadorRepository.findById(id).orElseThrow(EntregadorNotFoundException::new);

        if(!entregador.getStatusEstabelecimento().equalsIgnoreCase("APROVADO"))
            throw new EntregadorNaoAprovadoException();
        else if(!entregador.getCodigoAcesso().equals(entregador.getCodigoAcesso()))
            throw new IncorretCodigoAcessoException();

        entregador.setDisponibilidade(entregadorRequestDTO.getStatus());

        entregador = this.entregadorRepository.save(entregador);

        return modelMapper.map(entregador, EntregadorResponseDTO.class);
    }

    private Entregador getEntregadorId(Long id) throws EntregadorNotFoundException {
        return entregadorRepository.findById(id)
                .orElseThrow(() -> new EntregadorNotFoundException());
    }

    public EntregadorResponseDTO getEntregadorById(Long id) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        return modelMapper.map(entregador, EntregadorResponseDTO.class);
    }

    private boolean isEntregadorCadastrado(String nome) {
        try {
            getEntregadorByNomeCompleto(nome);
            return true;
        } catch (EntregadorNotFoundException e) {
            return false;
        }
    }

    public EntregadorRequestDTO getEntregadorByNomeCompleto(String codigo) throws EntregadorNotFoundException {
        Entregador entregador = entregadorRepository.findByNomeCompleto(codigo)
                .orElseThrow(() -> new EntregadorNotFoundException());
        return modelMapper.map(entregador, EntregadorRequestDTO.class);
    }

    private Veiculo salvarVeiculoEntregador(Veiculo veiculo) {
        return veiculoService.criaVeiculo(veiculo);
    }

    private void salvarEntregadorCadastrado(Entregador entregador) {
        entregadorRepository.save(entregador);
    }

}