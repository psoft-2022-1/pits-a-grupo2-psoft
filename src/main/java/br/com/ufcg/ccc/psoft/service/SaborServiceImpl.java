package br.com.ufcg.ccc.psoft.service;

import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.Sabor;
import br.com.ufcg.ccc.psoft.repository.SaborRepository;

@Service
public class SaborServiceImpl implements SaborService {

	@Autowired
	private SaborRepository saborRepository;

	@Autowired
	public ModelMapper modelMapper;

	public SaborDTO criarSabor(SaborDTO saborDTO) throws SaborAlreadyCreatedException {
		if (isSaborCadastrado(saborDTO.getId())) {
			throw new SaborAlreadyCreatedException();
		}

		Sabor sabor = new Sabor(saborDTO.getNomeSabor(), saborDTO.getTipo(), saborDTO.getValorMedio(),
				saborDTO.getValorGrande());

		salvarSabor(sabor);

		return modelMapper.map(sabor, SaborDTO.class);
	}

	private void salvarSabor(Sabor sabor) {
		saborRepository.save(sabor);
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

	public void removerSaborCadastrado(Long id) throws SaborNotFoundException {
		Sabor sabor = getSaborId(id);
		saborRepository.delete(sabor);
	}
}
