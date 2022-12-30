package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.SenhaInvalidaException;
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

    public EntregadorDTO criaEntregador(EntregadorDTO entregadorDTO) throws EntregadorAlreadyCreatedException, SenhaInvalidaException {

        if(isEntregadorCadastrado(entregadorDTO.getNomeCompleto()))
            throw new EntregadorAlreadyCreatedException();

        if(entregadorDTO.getCodigoAcesso().length() != 6)
            throw new SenhaInvalidaException();

        Veiculo veiculo = salvarVeiculoEntregador(entregadorDTO.getVeiculo());
        Entregador entregador = new Entregador(entregadorDTO, veiculo);
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    public List<EntregadorDTO> listarEntregadores() {
        List<EntregadorDTO> entregadores = entregadorRepository.findAll()
                .stream()
                .map(entregador -> modelMapper.map(entregador, EntregadorDTO.class))
                .collect(Collectors.toList());
        return entregadores;
    }

    public void removerEntregadorCadastrado(Long id) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        entregadorRepository.delete(entregador);
    }

    public EntregadorDTO atualizaEntregador(Long id, EntregadorDTO entregadorDTO) throws EntregadorNotFoundException, SenhaInvalidaException, IncorretCodigoAcessoException {

        Entregador entregador = getEntregadorId(id);

        if(!entregador.getCodigoAcesso().equals(entregadorDTO.getCodigoAcesso()))
            throw new IncorretCodigoAcessoException();
        if(entregadorDTO.getCodigoAcesso().length() != 6)
            throw new SenhaInvalidaException();

        entregador.setNomeCompleto(entregadorDTO.getNomeCompleto());
        entregador.setCodigoAcesso(entregadorDTO.getCodigoAcesso());
        entregador.setVeiculo(entregadorDTO.getVeiculo());
        salvarEntregadorCadastrado(entregador);

        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    private Entregador getEntregadorId(Long id) throws EntregadorNotFoundException {
        return entregadorRepository.findById(id)
                .orElseThrow(() -> new EntregadorNotFoundException());
    }

    public EntregadorDTO getEntregadorById(Long id) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    private boolean isEntregadorCadastrado(String nome) {
        try {
            getEntregadorByNomeCompleto(nome);
            return true;
        } catch (EntregadorNotFoundException e) {
            return false;
        }
    }

    public EntregadorDTO getEntregadorByNomeCompleto(String codigo) throws EntregadorNotFoundException {
        Entregador entregador = entregadorRepository.findByNomeCompleto(codigo)
                .orElseThrow(() -> new EntregadorNotFoundException());
        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    private Veiculo salvarVeiculoEntregador(Veiculo veiculo) {
        return veiculoService.criaVeiculo(veiculo);
    }

    private void salvarEntregadorCadastrado(Entregador entregador) {
        entregadorRepository.save(entregador);
    }

}