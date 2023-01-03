package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.FuncionarioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;


public interface FuncionarioService {

    public EntregadorResponseDTO analisarEntregador (AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) throws EntregadorNotFoundException, EstabelecimentoNotFoundException, FuncionarioNotFoundException, IncorretCodigoAcessoException;

    public boolean checkCodAcesso(FuncionarioRequestDTO funcionarioRequestDTO, String codFuncionario) throws IncorretCodigoAcessoException;

    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) throws FuncionarioAlreadyCreatedException, InvalidCodigoAcessoException;

    public void removerFuncionarioCadastrado(Long id) throws FuncionarioNotFoundException;
}
