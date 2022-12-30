package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.FuncionarioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.Entregador;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;
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

    public EntregadorRequestDTO analisarEntregador(AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) throws EntregadorNotFoundException, EstabelecimentoNotFoundException, FuncionarioNotFoundException, IncorretCodigoAcessoException {
        Estabelecimento estabelecimento = estabelecimentoService.getEstabelecimentoById(analisarEntregadorRequestDTO.getIdEstabelecimento());
        Funcionario funcionario = getFuncionarioId(analisarEntregadorRequestDTO.getIdFuncionario());

        if(!estabelecimento.getCodigoAcesso().equals(analisarEntregadorRequestDTO.getCodEstabelecimento())){
            throw new IncorretCodigoAcessoException();
        }
        if(!funcionario.getCodigoAcesso().equals(analisarEntregadorRequestDTO.getCodFuncionario())){
            throw new IncorretCodigoAcessoException();
        }

        Optional<Entregador> optionalEntregador = entregadorRepository.findById(analisarEntregadorRequestDTO.getIdEntregador());
        if (optionalEntregador.isPresent()){
            throw new EntregadorNotFoundException();
        }

        Entregador entregador = optionalEntregador.get();
        entregador.setStatusEstabelecimento(analisarEntregadorRequestDTO.getStatus());
        entregadorRepository.save(entregador);

        return modelMapper.map(entregador, EntregadorRequestDTO.class);
    }

    public FuncionarioRequestDTO getById(long idFuncionario) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioId(idFuncionario);
        return modelMapper.map(funcionario, FuncionarioRequestDTO.class);
    }

    @Override
    public boolean checkCodAcesso(FuncionarioRequestDTO funcionarioRequestDTO, String codFuncionario) throws IncorretCodigoAcessoException {
        if(!funcionarioRequestDTO.getCodigoAcesso().equals(codFuncionario)){
            throw new IncorretCodigoAcessoException();
        }
        return true;
    }

    private Funcionario getFuncionarioId(Long id) throws FuncionarioNotFoundException {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundException());
    }
    public FuncionarioRequestDTO getFuncionarioById(Long id) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioId(id);
        return modelMapper.map(funcionario, FuncionarioRequestDTO.class);
    }
    @Override
    public FuncionarioResponseDTO criaFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) throws FuncionarioAlreadyCreatedException, InvalidCodigoAcessoException {
        if(funcionarioRequestDTO.getCodigoAcesso().length() != 6){
            throw new InvalidCodigoAcessoException();
        }
        Funcionario funcionario = new Funcionario(funcionarioRequestDTO.getNomeCompleto(), funcionarioRequestDTO.getCodigoAcesso());

        this.funcionarioRepository.save(funcionario);

        return modelMapper.map(funcionario, FuncionarioResponseDTO.class);
    }

    @Override
    public void removeFuncionarioCadastrado(Long id) throws FuncionarioNotFoundException {
        Funcionario funcionario = getFuncionarioId(id);
        funcionarioRepository.delete(funcionario);
    }

}
