package br.com.ufcg.ccc.psoft.util;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.FuncionarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {

	static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario com id %s não foi encontrado.";
	static final String FUNCIONARIO_JA_CADASTRADO = "O funcionario nome %s já esta cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioNaoEncontrado() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_NAO_ENCONTRADO)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> erroFuncionarioJaCadastrado(FuncionarioDTO funcionarioDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_JA_CADASTRADO,
				funcionarioDTO.getNomeCompleto())), HttpStatus.CONFLICT);
	}


}
