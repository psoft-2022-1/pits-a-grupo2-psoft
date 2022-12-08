package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroFuncionario {

	static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario com id %s não foi encontrado.";
	
	public static ResponseEntity<CustomErrorType> erroFuncionarioNaoEncontrado() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroFuncionario.FUNCIONARIO_NAO_ENCONTRADO)),
				HttpStatus.NOT_FOUND);
	}


}
