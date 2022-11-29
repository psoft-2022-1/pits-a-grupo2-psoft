package br.com.ufcg.ccc.psoft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPagamento {

	static final String PAGAMENTO_NAO_ENCONTRADO = "Pagamento com id %s não foi encontrado.";
	
	public static ResponseEntity<CustomErrorType> erroProdutoNaoEncontradoNoCarrinho(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPagamento.PAGAMENTO_NAO_ENCONTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
}
