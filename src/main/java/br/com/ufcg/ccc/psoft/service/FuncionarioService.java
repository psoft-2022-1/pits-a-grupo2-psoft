package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import br.com.ufcg.ccc.psoft.dto.FuncionarioDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.FuncionarioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;


public interface FuncionarioService {

    EntregadorDTO analisarEntregador (AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) throws EntregadorNotFoundException, EstabelecimentoNotFoundException, FuncionarioNotFoundException, IncorretCodigoAcessoException;

    FuncionarioDTO getById(long idFuncionario) throws FuncionarioNotFoundException;

    boolean checkCodAcesso(FuncionarioDTO funcionarioDTO, String codFuncionario) throws IncorretCodigoAcessoException;
}
