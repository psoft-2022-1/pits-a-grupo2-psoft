package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroEstabelecimento {

	   static final String ESTABELECIMENTO_NAO_CASTRADO = "Estabelecimento com id %s não está cadastrado";

	    public static ResponseEntity<CustomErrorType> erroEntregadorNaoEncontrado(long id) {
	        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroEstabelecimento.ESTABELECIMENTO_NAO_CASTRADO, id)),
	                HttpStatus.NOT_FOUND);
	    }
}
