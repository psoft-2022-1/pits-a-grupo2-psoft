package br.com.ufcg.ccc.psoft.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
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

    public SaborDTO criarSabor(Long idEstabelecimento, SaborDTO saborDTO)
            throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException {
        if (isSaborCadastrado(saborDTO.getId())) {
            throw new SaborAlreadyCreatedException();
        }

        Sabor sabor = new Sabor(saborDTO.getNomeSabor(), saborDTO.getTipo(), saborDTO.getValorMedio(),
                saborDTO.getValorGrande());

        // e necessario salvar no repositorio de sabor e em cardapio
        salvarSabor(sabor);
        salvarSaborNoCardapio(idEstabelecimento, sabor);

        return modelMapper.map(sabor, SaborDTO.class);
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

    public SaborDTO getSaborById(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException {

        Sabor sabor = getSaborId(idEstabelecimento, idSabor);
        return modelMapper.map(sabor, SaborDTO.class);
    }

    private Sabor getSaborId(Long id) throws SaborNotFoundException {
        return saborRepository.findById(id)
                .orElseThrow(() -> new SaborNotFoundException());
    }

    public SaborDTO getSaborById(Long id) throws SaborNotFoundException {
        Sabor sabor = getSaborId(id);
        return modelMapper.map(sabor, SaborDTO.class);
    }

    private Sabor getSaborId(Long idEstabelecimento, Long idSabor)
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
            return sabor;
        }
    }

    public SaborDTO atualizarSabor(Long idEstabelecimento, Long idSabor, SaborDTO saborDTO)
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
            sabor.setNomeSabor(saborDTO.getNomeSabor());
            sabor.setTipo(saborDTO.getTipo());
            sabor.setValorMedio(saborDTO.getValorMedio());
            sabor.setValorGrande(saborDTO.getValorGrande());

            salvarSabor(sabor);

        }

        return modelMapper.map(sabor, SaborDTO.class);
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

    public List<SaborDTO> listarSabores() {
        List<SaborDTO> sabores = saborRepository.findAll()
                .stream()
                .map(sabor -> modelMapper.map(sabor, SaborDTO.class))
                .collect(Collectors.toList());
        return sabores;
    }
}

