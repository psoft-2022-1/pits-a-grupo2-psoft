package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;

public interface SaborService {

    public SaborDTO criarSabor(Long idEstabelecimento, SaborDTO saborDTO)
            throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException;

    public SaborDTO getSaborById(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public SaborDTO atualizarSabor(Long idEstabelecimento, Long idSabor, SaborDTO saborDTO)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public void removerSaborCadastrado(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public List<SaborDTO> listarSabores();

    public SaborDTO getSaborById(Long id) throws SaborNotFoundException;

}