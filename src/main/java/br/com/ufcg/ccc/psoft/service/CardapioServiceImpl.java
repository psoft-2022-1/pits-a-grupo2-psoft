package br.com.ufcg.ccc.psoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.SaborResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.repository.CardapioRepository;

@Service
public class CardapioServiceImpl implements CardapioService {

    @Autowired
    CardapioRepository cardapioRepository;

    @Override
    public CardapioResponseDTO consultaCardapioPorSabor(Long idCardapio, String tipoSabor) throws CardapioNotFoundException {
        Optional<Cardapio> opCardapio = cardapioRepository.findById(idCardapio);
        if (opCardapio.isEmpty())
            throw new CardapioNotFoundException();
        List<SaborResponseDTO> sabores = opCardapio.get().getSabores().stream().filter(s -> s.getTipo().equals(tipoSabor)).map(SaborResponseDTO::new).collect(Collectors.toList());
        
        List<SaborResponseDTO> saboresDisponiveis = new ArrayList<>();
        List<SaborResponseDTO> saboresIndisponiveis = new ArrayList<>();
        
        for(SaborResponseDTO SaborResponseDTO:sabores) {
        	if(SaborResponseDTO.isEstaDisponivel()) {
        		saboresDisponiveis.add(SaborResponseDTO);
        	}else {
        		saboresIndisponiveis.add(SaborResponseDTO);
        	}
        }
        
        List<SaborResponseDTO> saboresOrdenados = new ArrayList<>();
        
        for(SaborResponseDTO SaborResponseDTO:saboresDisponiveis) {
        	saboresOrdenados.add(SaborResponseDTO);
        }
        
        for(SaborResponseDTO SaborResponseDTO:saboresIndisponiveis) {
        	saboresOrdenados.add(SaborResponseDTO);
        }
        
        
        return new CardapioResponseDTO(opCardapio.get().getId(), saboresOrdenados);
    }
}
