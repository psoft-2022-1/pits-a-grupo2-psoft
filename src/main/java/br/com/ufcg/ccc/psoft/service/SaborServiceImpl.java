package br.com.ufcg.ccc.psoft.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;
import br.com.ufcg.ccc.psoft.model.Sabor;
import br.com.ufcg.ccc.psoft.repository.EstabelecimentoRepository;
import br.com.ufcg.ccc.psoft.repository.SaborRepository;
import br.com.ufcg.ccc.psoft.repository.CardapioRepository;

@Service
public class SaborServiceImpl implements SaborService {

    @Autowired
    private SaborRepository saborRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    public ModelMapper modelMapper;

    public SaborRequestDTO criarSabor(Long idEstabelecimento, SaborRequestDTO saborRequestDTO)
            throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException {
        if (isSaborCadastrado(saborRequestDTO.getId())) {
            throw new SaborAlreadyCreatedException();
        }

        Sabor sabor = new Sabor(saborRequestDTO.getNomeSabor(), saborRequestDTO.getTipo(), saborRequestDTO.getValorMedio(),
                saborRequestDTO.getValorGrande());

        // e necessario salvar no repositorio de sabor e em cardapio
        salvarSabor(sabor);
        salvarSaborNoCardapio(idEstabelecimento, sabor);

        return modelMapper.map(sabor, SaborRequestDTO.class);
    }

    private void salvarSabor(Sabor sabor) {
        saborRepository.save(sabor);
    }

    private void salvarSaborNoCardapio(Long idEstabelecimento, Sabor sabor) throws EstabelecimentoNotFoundException {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        Cardapio cardapio = estabelecimento.getCardapio();

        cardapio.adicionarSabor(sabor);

        cardapioRepository.save(cardapio);

    }

    private boolean isSaborCadastrado(Long id) {
        try {
            saborRepository.findById(id).orElseThrow(() -> new SaborNotFoundException());
            return true;
        } catch (SaborNotFoundException e) {
            return false;
        }
    }

    public SaborRequestDTO getSaborById(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException {

        Sabor sabor = getSaborId(idEstabelecimento, idSabor);
        return modelMapper.map(sabor, SaborRequestDTO.class);
    }

    private Sabor getSaborId(Long id) throws SaborNotFoundException {
        return saborRepository.findById(id)
                .orElseThrow(() -> new SaborNotFoundException());
    }

    public SaborRequestDTO getSaborById(Long id) throws SaborNotFoundException {
        Sabor sabor = getSaborId(id);
        return modelMapper.map(sabor, SaborRequestDTO.class);
    }

    private Sabor getSaborId(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        Cardapio cardapio = estabelecimento.getCardapio();

        for (Sabor s : cardapio.getSabores()) {
            if (s.getId().equals(idSabor)) {
                return s;
            }
        }
        throw new SaborNotFoundException();

    }

    public SaborRequestDTO atualizarSabor(Long idEstabelecimento, Long idSabor, SaborRequestDTO saborRequestDTO)
            throws SaborNotFoundException, EstabelecimentoNotFoundException {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        Cardapio cardapio = estabelecimento.getCardapio();

        Sabor sabor = null;

        for (Sabor s : cardapio.getSabores()) {
            if (s.getId().equals(idSabor)) {
                sabor = s;
            }
        }

        if (sabor == null) {
            throw new SaborNotFoundException();
        } else {
            sabor.setNomeSabor(saborRequestDTO.getNomeSabor());
            sabor.setTipo(saborRequestDTO.getTipo());
            sabor.setValorMedio(saborRequestDTO.getValorMedio());
            sabor.setValorGrande(saborRequestDTO.getValorGrande());

            salvarSabor(sabor);

        }

        return modelMapper.map(sabor, SaborRequestDTO.class);
    }

    public void removerSaborCadastrado(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        Cardapio cardapio = estabelecimento.getCardapio();

        Sabor sabor = null;

        for (Sabor s : cardapio.getSabores()) {
            if (s.getId().equals(idSabor)) {
                sabor = s;
            }
        }

        if (sabor == null) {
            throw new SaborNotFoundException();
        } else {
            saborRepository.delete(sabor);
            deletarSaborDoCardapio(cardapio, sabor);
        }
    }

    private void deletarSaborDoCardapio(Cardapio cardapio, Sabor sabor) {

        List<Sabor> sabores = cardapio.getSabores();

        sabores.remove(sabor);

        cardapio.setSabores(sabores);
        cardapioRepository.save(cardapio);
    }

    public List<SaborRequestDTO> listarSabores() {
        List<SaborRequestDTO> sabores = saborRepository.findAll()
                .stream()
                .map(sabor -> modelMapper.map(sabor, SaborRequestDTO.class))
                .collect(Collectors.toList());
        return sabores;
    }

}

