package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import br.com.ufcg.ccc.psoft.exception.*;


public interface FuncionarioService {

    EntregadorRequestDTO analisarEntregador (AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) throws EntregadorNotFoundException, EstabelecimentoNotFoundException, FuncionarioNotFoundException, IncorretCodigoAcessoException;

    FuncionarioRequestDTO getById(long idFuncionario) throws FuncionarioNotFoundException;

    boolean checkCodAcesso(FuncionarioRequestDTO funcionarioRequestDTO, String codFuncionario) throws IncorretCodigoAcessoException;

    FuncionarioRequestDTO criaFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) throws FuncionarioAlreadyCreatedException, InvalidCodigoAcessoException;

    void removerFuncionarioCadastrado(Long id) throws FuncionarioNotFoundException;
}
