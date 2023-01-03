package br.com.ufcg.ccc.psoft.dto.requests;

import java.util.List;

import lombok.Data;

@Data
public class PedidoRequestDTO {

   	private String codCliente;

   	private Long idEstabelecimento; 
   	
	private List <ItemDePedidoRequestDTO> itensEscolhidos;
   	
    private String tipoPagamento;

	private String enderecoEntrega;
	
}
