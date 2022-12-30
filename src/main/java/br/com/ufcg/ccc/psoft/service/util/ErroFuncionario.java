package br.com.ufcg.ccc.psoft.service.util;

import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {

	static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario com id %s não foi encontrado.";
	static final String FUNCIONARIO_JA_CADASTRADO = "O funcionario nome %s já esta cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioNaoEncontrado() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_NAO_ENCONTRADO)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> erroFuncionarioJaCadastrado(FuncionarioRequestDTO funcionarioRequestDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_JA_CADASTRADO,
				funcionarioRequestDTO.getNomeCompleto())), HttpStatus.CONFLICT);
	}


}
