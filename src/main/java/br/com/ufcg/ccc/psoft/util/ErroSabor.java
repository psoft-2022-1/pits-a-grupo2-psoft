package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;

public class ErroSabor {

	static final String SABOR_NAO_ENCONTRADO = "Sabor com id %s não foi encontrado.";
	
	static final String ALGUM_DOS_SABORES_NAO_ENCONTRADO = "Algum dos sabores do pedido nao foi encontrado.";

	static final String SABOR_JA_CADASTRADO = "O sabor %s de nome %s ja esta cadastrado";

	static final String SABOR_JA_DISPONIVEL = "O sabor de id %s  ja esta disponivel no cardapio";
	
	static final String NOME_E_ID_NAO_CORRESPONDEM = "O nome e o id de algum dos sabores nao correspondem";

	static final String ALGUM_DOS_SABORES_NAO_ESTA_DISPONIVEL = "Algum dos sabores do pedido nao esta disponivel";
	
	public static ResponseEntity<CustomErrorType> erroSaborNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroSabor.SABOR_NAO_ENCONTRADO, id)), HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> erroSaborJaCadastrado(String nomeSabor) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroSabor.SABOR_JA_CADASTRADO, nomeSabor)),
				HttpStatus.CONFLICT);
	}

	public static ResponseEntity<?> erroSaborJaDisponivelNoCardapio(long idSabor) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroSabor.SABOR_JA_DISPONIVEL, idSabor)), HttpStatus.CONFLICT);
	}

	public static ResponseEntity<?> erroAlgumDosSaboresNaoEncontrado() {

		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ErroSabor.ALGUM_DOS_SABORES_NAO_ENCONTRADO), HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> erroNomeEIdDeSaborNaoCorrespondem() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ErroSabor.NOME_E_ID_NAO_CORRESPONDEM ), HttpStatus.CONFLICT);
	}

	public static ResponseEntity<?> saborNaoEstaDisponivel() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(ErroSabor.ALGUM_DOS_SABORES_NAO_ESTA_DISPONIVEL), HttpStatus.NOT_FOUND);
	}
}
