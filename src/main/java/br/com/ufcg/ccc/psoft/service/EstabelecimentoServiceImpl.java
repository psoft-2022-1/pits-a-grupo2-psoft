package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.CardapioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EstabelecimentoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EstabelecimentoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
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
    public EstabelecimentoRequestDTO getById(Long idEstabelecimento) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = getEstabelecimentoById(idEstabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoRequestDTO.class);
    }

    @Override
    public boolean checkCodAcesso(EstabelecimentoRequestDTO estabelecimentoDTO, String codEstabelecimento) throws IncorretCodigoAcessoException {
        if (!estabelecimentoDTO.getCodigoAcesso().equals(codEstabelecimento)) {
            throw new IncorretCodigoAcessoException();
        }
        return true;
    }

    public Estabelecimento getEstabelecimentoById(Long id) throws EstabelecimentoNotFoundException {
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
    public CardapioResponseDTO consultaCarcapioPorSabor(Long idEstabelecimento, String tipoSabor) throws EstabelecimentoNotFoundException, CardapioNotFoundException {
        Long idCardapio = getEstabelecimentoPorId(idEstabelecimento).getCardapio().getId();
        return cardapioService.consultaCardapioPorSabor(idCardapio, tipoSabor);
    }

    @Override
    public EstabelecimentoResponseDTO criarEstabelecimento(EstabelecimentoRequestDTO estabelecimentoDTO) throws InvalidCodigoAcessoException {
        if(estabelecimentoDTO.getCodigoAcesso().length() != 6){
            throw new InvalidCodigoAcessoException();
        }
        Estabelecimento estabelecimento = new Estabelecimento(estabelecimentoDTO.getCodigoAcesso());

        estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoResponseDTO.class);
    }

    @Override
    public EstabelecimentoRequestDTO editarEstabelecimento(Long idEstabelecimento, EstabelecimentoRequestDTO estabelecimentoDTO) throws EstabelecimentoNotFoundException {
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(idEstabelecimento);
        if(estabelecimento.isEmpty())
            throw new EstabelecimentoNotFoundException();
        estabelecimento.get().setCodigoAcesso(estabelecimentoDTO.getCodigoAcesso());

        return modelMapper.map(estabelecimentoRepository.save(estabelecimento.get()), EstabelecimentoRequestDTO.class);
    }

    @Override
    public CardapioResponseDTO getCardapio(Long idEstabelecimento) throws EstabelecimentoNotFoundException {
        return modelMapper.map(getEstabelecimentoById(idEstabelecimento).getCardapio(), CardapioResponseDTO.class);
    }
}
