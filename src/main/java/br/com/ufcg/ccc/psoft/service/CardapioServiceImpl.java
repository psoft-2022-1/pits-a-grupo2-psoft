package br.com.ufcg.ccc.psoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.CardapioDTO;
import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.repository.CardapioRepository;

@Service
public class CardapioServiceImpl implements CardapioService {

    @Autowired
    CardapioRepository cardapioRepository;

    @Override
    public CardapioDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor) throws CardapioNotFoundException {
        Optional<Cardapio> opCardapio = cardapioRepository.findById(idCardapio);
        if (opCardapio.isEmpty())
            throw new CardapioNotFoundException();
        List<SaborDTO> sabores = opCardapio.get().getSabores().stream().filter(s -> s.getTipo().equals(tipoSabor)).map(SaborDTO::new).collect(Collectors.toList());
        
        List<SaborDTO> saboresDisponiveis = new ArrayList<>();
        List<SaborDTO> saboresIndisponiveis = new ArrayList<>();
        
        for(SaborDTO saborDTO:sabores) {
        	if(saborDTO.isEstaDisponivel()) {
        		saboresDisponiveis.add(saborDTO);
        	}else {
        		saboresIndisponiveis.add(saborDTO);
        	}
        }
        
        List<SaborDTO> saboresOrdenados = new ArrayList<>();
        
        for(SaborDTO saborDTO:saboresDisponiveis) {
        	saboresOrdenados.add(saborDTO);
        }
        
        for(SaborDTO saborDTO:saboresIndisponiveis) {
        	saboresOrdenados.add(saborDTO);
        }
        
        
        return new CardapioDTO(opCardapio.get().getId(), saboresOrdenados);
    }
}
