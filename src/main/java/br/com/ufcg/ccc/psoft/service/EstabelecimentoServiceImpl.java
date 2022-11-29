package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;
import br.com.ufcg.ccc.psoft.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService{

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    public ModelMapper modelMapper;
    @Override
    public EstabelecimentoDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = getEstabelecimentoById(idEstabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    @Override
    public boolean checkCodAcesso(EstabelecimentoDTO estabelecimentoDTO, String codEstabelecimento) throws IncorretCodigoAcessoException {
        if(!estabelecimentoDTO.getCodigoAcesso().equals(codEstabelecimento)){
            throw new IncorretCodigoAcessoException();
        }
        return true;
    }

    private Estabelecimento getEstabelecimentoById(Long id) throws EstabelecimentoNotFoundException {
        return estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());
    }
}
