package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;

public class ErroSabor {

	static final String SABOR_NAO_ENCONTRADO = "Sabor com id %s não foi encontrado.";
	
	static final String SABOR_JA_CADASTRADO = "O sabor %s de nome %s já esta cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroSaborNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroSabor.SABOR_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	

	public static ResponseEntity<?> erroSaborJaCadastrado(SaborDTO saborDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroSabor.SABOR_JA_CADASTRADO,saborDTO.getNomeSabor())), HttpStatus.CONFLICT);
	}
}
