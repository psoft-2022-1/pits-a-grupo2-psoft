package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;

public interface SaborService {

    public SaborRequestDTO criarSabor(Long idEstabelecimento, SaborRequestDTO saborRequestDTO)
            throws SaborAlreadyCreatedException, EstabelecimentoNotFoundException;

    public SaborRequestDTO getSaborById(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public SaborRequestDTO atualizarSabor(Long idEstabelecimento, Long idSabor, SaborRequestDTO saborRequestDTO)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public void removerSaborCadastrado(Long idEstabelecimento, Long idSabor)
            throws SaborNotFoundException, EstabelecimentoNotFoundException;

    public List<SaborRequestDTO> listarSabores();

    public SaborRequestDTO getSaborById(Long id) throws SaborNotFoundException;

}