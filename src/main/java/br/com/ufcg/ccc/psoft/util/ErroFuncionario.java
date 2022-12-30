package br.com.ufcg.ccc.psoft.util;

import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {

	static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario com id %s não foi encontrado.";
	static final String FUNCIONARIO_JA_CADASTRADO = "O funcionario nome %s já esta cadastrado";

	static final String CODIGO_ACESSO_INVALIDO = "O código de acesso deve possuir 6 dígitos.";
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioNaoEncontrado() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_NAO_ENCONTRADO)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroFuncionario.CODIGO_ACESSO_INVALIDO),
				HttpStatus.NOT_ACCEPTABLE);
	}
	public static ResponseEntity<?> erroFuncionarioJaCadastrado(FuncionarioRequestDTO funcionarioRequestDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_JA_CADASTRADO,
				funcionarioRequestDTO.getNomeCompleto())), HttpStatus.CONFLICT);
	}


}
