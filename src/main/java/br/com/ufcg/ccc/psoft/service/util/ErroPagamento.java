package br.com.ufcg.ccc.psoft.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPagamento {

	static final String PAGAMENTO_NAO_ENCONTRADO = "Pagamento com id %s não foi encontrado.";

	static final String PAGAMENTO_NAO_VALIDO = "O tipo de pagamento %s não é válido.";

	public static ResponseEntity<CustomErrorType> erroPagamentoNaoValido(String tipoPagamento) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPagamento.PAGAMENTO_NAO_VALIDO, tipoPagamento)),
				HttpStatus.NOT_FOUND);
	}
}
