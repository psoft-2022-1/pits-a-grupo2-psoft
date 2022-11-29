package br.com.ufcg.ccc.psoft.service;

import java.util.List;

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

	public SaborDTO criarSabor(long idEstabelecimento, SaborDTO saborDTO)
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

	private void salvarSaborNoCardapio(long idEstabelecimento, Sabor sabor) throws EstabelecimentoNotFoundException {

		Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
				.orElseThrow(() -> new EstabelecimentoNotFoundException());

		Cardapio cardapio = estabelecimento.getCardapio();

		cardapio.adicionarSabor(sabor);
		estabelecimento.setCardapio(cardapio);
		
		estabelecimentoRepository.save(estabelecimento);
		cardapioRepository.save(cardapio);

	}

	private boolean isSaborCadastrado(Long id) {
		try {
			getSaborById(id);
			return true;
		} catch (SaborNotFoundException e) {
			return false;
		}
	}

	public SaborDTO getSaborById(Long id) throws SaborNotFoundException {
		Sabor sabor = getSaborId(id);
		return modelMapper.map(sabor, SaborDTO.class);
	}

	private Sabor getSaborId(Long id) throws SaborNotFoundException {
		return saborRepository.findById(id).orElseThrow(() -> new SaborNotFoundException());
	}

	public SaborDTO atualizarSabor(Long idSabor, SaborDTO saborDTO) throws SaborNotFoundException {

		Sabor sabor = getSaborId(idSabor);

		sabor.setNomeSabor(saborDTO.getNomeSabor());
		sabor.setTipo(saborDTO.getTipo());
		sabor.setValorMedio(saborDTO.getValorMedio());
		sabor.setValorGrande(saborDTO.getValorGrande());

		salvarSabor(sabor);

		return modelMapper.map(sabor, SaborDTO.class);
	}

	public void removerSaborCadastrado(long idEstabelecimento, Long idSabor)
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
			deletarSaborDoCardapio(estabelecimento,cardapio, sabor);
		}
	}

	private void deletarSaborDoCardapio(Estabelecimento estabelecimento, Cardapio cardapio, Sabor sabor) {

		List<Sabor> sabores = cardapio.getSabores();

		sabores.remove(sabor);

		cardapio.setSabores(sabores);
		estabelecimento.setCardapio(cardapio);
		
		estabelecimentoRepository.save(estabelecimento);
		cardapioRepository.save(cardapio);
	}
}
