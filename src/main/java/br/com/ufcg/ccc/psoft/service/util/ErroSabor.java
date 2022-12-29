package br.com.ufcg.ccc.psoft.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;

public class ErroSabor {

	static final String SABOR_NAO_ENCONTRADO = "Sabor com id %s não foi encontrado.";
	
	static final String SABOR_JA_CADASTRADO = "O sabor %s de nome %s já esta cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroSaborNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroSabor.SABOR_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	

	public static ResponseEntity<?> erroSaborJaCadastrado(SaborRequestDTO saborRequestDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroSabor.SABOR_JA_CADASTRADO, saborRequestDTO.getNomeSabor())), HttpStatus.CONFLICT);
	}
}
