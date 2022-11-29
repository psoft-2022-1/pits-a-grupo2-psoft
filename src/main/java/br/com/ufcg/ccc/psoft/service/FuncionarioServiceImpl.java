package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.dto.FuncionarioDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.FuncionarioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
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

    private Funcionario getFuncionarioById(long idFuncionario) throws FuncionarioNotFoundException {
        return funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new FuncionarioNotFoundException());
    }

}
