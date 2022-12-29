package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.*;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Entregador;
import br.com.ufcg.ccc.psoft.model.Funcionario;
import br.com.ufcg.ccc.psoft.repository.EntregadorRepository;
import br.com.ufcg.ccc.psoft.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;
    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    public ModelMapper modelMapper;

    public EntregadorDTO analisarEntregador(AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) throws EntregadorNotFoundException, EstabelecimentoNotFoundException, FuncionarioNotFoundException, IncorretCodigoAcessoException {
        EstabelecimentoDTO estabelecimentoDTO = estabelecimentoService.getById(analisarEntregadorRequestDTO.getIdEstabelecimento());
        FuncionarioDTO funcionarioDTO = getById(analisarEntregadorRequestDTO.getIdFuncionario());

        if(!estabelecimentoDTO.getCodigoAcesso().equals(analisarEntregadorRequestDTO.getCodEstabelecimento())){
            throw new IncorretCodigoAcessoException();
        }
        if(!funcionarioDTO.getCodigoAcesso().equals(analisarEntregadorRequestDTO.getCodFuncionario())){
            throw new IncorretCodigoAcessoException();
        }

        Optional<Entregador> optionalEntregador = entregadorRepository.findById(analisarEntregadorRequestDTO.getIdEntregador());
        if (optionalEntregador.isPresent()){
            throw new EntregadorNotFoundException();
        }

        Entregador entregador = optionalEntregador.get();
        entregador.setStatusEstabelecimento(analisarEntregadorRequestDTO.getStatus());
        entregadorRepository.save(entregador);

        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    public FuncionarioDTO getById(long idFuncionario) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioById(idFuncionario);
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    @Override
    public boolean checkCodAcesso(FuncionarioDTO funcionarioDTO, String codFuncionario) throws IncorretCodigoAcessoException {
        if(!funcionarioDTO.getCodigoAcesso().equals(codFuncionario)){
            throw new IncorretCodigoAcessoException();
        }
        return true;
    }

    private Funcionario getFuncionarioId(Long id) throws FuncionarioNotFoundException {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundException());
    }
    public FuncionarioDTO getFuncionarioById(Long id) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioId(id);
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }
    private boolean isFuncionarioCadastrado(Long id) {
        try {
            getFuncionarioById(id);
            return true;
        } catch (FuncionarioNotFoundException e) {
            return false;
        }
    }
    @Override
    public FuncionarioDTO criaFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioAlreadyCreatedException {
        if(isFuncionarioCadastrado(funcionarioDTO.getId())){
            throw new FuncionarioAlreadyCreatedException();
        }
        Funcionario funcionario = new Funcionario(funcionarioDTO.getNomeCompleto(),funcionarioDTO.getCodigoAcesso());

        this.funcionarioRepository.save(funcionario);

        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    @Override
    public void removeFuncionarioCadastrado(Long id) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioId(id);
        funcionarioRepository.delete(funcionario);
    }

    private Funcionario getFuncionarioById(long idFuncionario) throws FuncionarioNotFoundException {
        return funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new FuncionarioNotFoundException());
    }

}
