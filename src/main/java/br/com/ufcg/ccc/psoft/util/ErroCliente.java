package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;

public class ErroCliente {

static final String CLIENTE_NAO_CASTRADO = "Cliente com id %s não está cadastrado";
	
	static final String CLIENTES_NAO_CASTRADOS = "Não há clienters cadastrados";

	static final String NAO_FOI_POSSIVEL_ATUALIZAR = "Não foi possível atualizar a situação do cliente %s "
			+ "nome %s";

	static final String CLIENTE_JA_CADASTRADO = "O cliente nome %s já esta cadastrado com o enderecoPrincipal %s";

	static final String CODIGO_ACESSO_INVALIDO = "O código de acesso deve possuir 6 dígitos.";

	
	public static ResponseEntity<CustomErrorType> erroClienteNaoEnconrtrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCliente.CLIENTE_NAO_CASTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroSemClientesCadastrados() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCliente.CLIENTES_NAO_CASTRADOS),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCliente.CODIGO_ACESSO_INVALIDO),
				HttpStatus.NOT_ACCEPTABLE);
	}

	public static ResponseEntity<?> erroClienteJaCadastrado(ClienteRequestDTO clienteRequestDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCliente.CLIENTE_JA_CADASTRADO,
				clienteRequestDTO.getNomeCompleto(), clienteRequestDTO.getEnderecoPrincipal())), HttpStatus.CONFLICT);
	}
}
