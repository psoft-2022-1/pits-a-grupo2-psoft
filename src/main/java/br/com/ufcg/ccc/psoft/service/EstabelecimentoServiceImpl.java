package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.CardapioDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;
import br.com.ufcg.ccc.psoft.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    CardapioService cardapioService;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public EstabelecimentoDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = getEstabelecimentoById(idEstabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    @Override
    public boolean checkCodAcesso(EstabelecimentoDTO estabelecimentoDTO, String codEstabelecimento) throws IncorretCodigoAcessoException {
        if (!estabelecimentoDTO.getCodigoAcesso().equals(codEstabelecimento)) {
            throw new IncorretCodigoAcessoException();
        }
        return true;
    }

    private Estabelecimento getEstabelecimentoById(Long id) throws EstabelecimentoNotFoundException {
        return estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());
    }

    private Estabelecimento getEstabelecimentoPorId(Long idEstabelecimeto) throws EstabelecimentoNotFoundException {
        Optional<Estabelecimento> opEstabelecimento = estabelecimentoRepository.findById(idEstabelecimeto);

        if (opEstabelecimento.isEmpty())
            throw new EstabelecimentoNotFoundException();
        return opEstabelecimento.get();
    }

    @Override
    public CardapioDTO consultaCarcapioPorSabor(Long idEstabelecimento, String tipoSabor) throws EstabelecimentoNotFoundException, CardapioNotFoundException {
        Long idCardapio = getEstabelecimentoPorId(idEstabelecimento).getCardapio().getId();
        return cardapioService.consultaCardapioPorSabor(idCardapio, tipoSabor);
    }

    @Override
    public EstabelecimentoDTO criarEstabelecimento(EstabelecimentoDTO estabelecimentoDTO) {

        Estabelecimento estabelecimento = new Estabelecimento(estabelecimentoDTO.getCodigoAcesso());

        estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    @Override
    public EstabelecimentoDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoDTO estabelecimentoDTO) throws EstabelecimentoNotFoundException {
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(idEstabelecimento);
        if(estabelecimento.isEmpty())
            throw new EstabelecimentoNotFoundException();
        estabelecimento.get().setCodigoAcesso(estabelecimentoDTO.getCodigoAcesso());

        return modelMapper.map(estabelecimentoRepository.save(estabelecimento.get()), EstabelecimentoDTO.class);
    }
}
