package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.Entregador;
import br.com.ufcg.ccc.psoft.model.Veiculo;
import br.com.ufcg.ccc.psoft.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    public ModelMapper modelMapper;

    public EntregadorResponseDTO criaEntregador(EntregadorRequestDTO entregadorRequestDTO) throws EntregadorAlreadyCreatedException, InvalidCodigoAcessoException {
        if(entregadorRequestDTO.getCodigoAcesso().length() != 6){
            throw new InvalidCodigoAcessoException();
        }

        if(isEntregadorCadastrado(entregadorRequestDTO.getNomeCompleto())) {
            throw new EntregadorAlreadyCreatedException();
        }

        Veiculo veiculo = new Veiculo(entregadorRequestDTO.getVeiculo().getPlacaVeiculo(), entregadorRequestDTO.getVeiculo().getCorVeiculo(), entregadorRequestDTO.getVeiculo().getTipoVeiculo());
        salvarVeiculoEntregador(veiculo);
        Entregador entregador = new Entregador(entregadorRequestDTO.getNomeCompleto(), veiculo, entregadorRequestDTO.getCodigoAcesso());
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorResponseDTO.class);
    }

    public List<EntregadorResponseDTO> listarEntregadores() {
        List<EntregadorResponseDTO> entregadores = entregadorRepository.findAll()
                .stream()
                .map(entregador -> modelMapper.map(entregador, EntregadorResponseDTO.class))
                .collect(Collectors.toList());
        return entregadores;
    }

    public void removerEntregadorCadastrado(Long idEntregador) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(idEntregador);
        entregadorRepository.delete(entregador);
    }

    public EntregadorRequestDTO atualizaEntregador(Long id, EntregadorRequestDTO entregadorRequestDTO) throws EntregadorNotFoundException {

        Entregador entregador = getEntregadorId(id);

        entregador.setNomeCompleto(entregadorRequestDTO.getNomeCompleto());
        entregador.setCodigoAcesso(entregadorRequestDTO.getCodigoAcesso());
        entregador.getVeiculo().setCorVeiculo(entregadorRequestDTO.getVeiculo().getCorVeiculo());
        entregador.getVeiculo().setPlacaVeiculo(entregadorRequestDTO.getVeiculo().getPlacaVeiculo());
        entregador.getVeiculo().setTipoVeiculo(entregadorRequestDTO.getVeiculo().getTipoVeiculo());
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorRequestDTO.class);
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