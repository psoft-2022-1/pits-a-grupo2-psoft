package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.CardapioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardapioServiceImpl implements CardapioService {

    @Autowired
    CardapioRepository cardapioRepository;

    @Override
    public CardapioRequestDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor) throws CardapioNotFoundException {
        Optional<Cardapio> opCardapio = cardapioRepository.findById(idCardapio);
        if (opCardapio.isEmpty())
            throw new CardapioNotFoundException();
        List<SaborRequestDTO> sabores = opCardapio.get().getSabores().stream().filter(s -> s.getTipo().equals(tipoSabor)).map(SaborRequestDTO::new).collect(Collectors.toList());
        return new CardapioRequestDTO(opCardapio.get().getId(), sabores);
    }
}
