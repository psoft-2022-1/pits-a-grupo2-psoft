package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;

public class ErroCliente {

    static final String CLIENTE_NAO_CASTRADO = "Cliente com id %s não está cadastrado";

	static final String CLIENTES_NAO_CASTRADOS = "Não há clienters cadastrados";

	static final String NAO_FOI_POSSIVEL_ATUALIZAR = "Não foi possível atualizar a situação do cliente %s "
			+ "nome %s";

	static final String SENHA_INVALIDA = "Senha invalida, por favor informe uma senha com 6 caracteres";

	static final String CLIENTE_JA_CADASTRADO = "O cliente nome %s já esta cadastrado com o enderecoPrincipal %s";

	static final String SENHA_INCORRETA = "Senha incorreta";


	public static ResponseEntity<CustomErrorType> erroClienteNaoEnconrtrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCliente.CLIENTE_NAO_CASTRADO, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroSemClientesCadastrados() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCliente.CLIENTES_NAO_CASTRADOS),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<?> erroClienteJaCadastrado(ClienteDTO clienteDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCliente.CLIENTE_JA_CADASTRADO,
				clienteDTO.getNomeCompleto(), clienteDTO.getEnderecoPrincipal())), HttpStatus.CONFLICT);
	}

	public static ResponseEntity<CustomErrorType> erroSenhaInvalida() {
		return new ResponseEntity<>(new CustomErrorType(SENHA_INVALIDA), HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<CustomErrorType> senhaIncorreta() {
		return new ResponseEntity<>(new CustomErrorType(SENHA_INCORRETA), HttpStatus.FORBIDDEN);
	}
}
