package br.com.ufcg.ccc.psoft.model.Enum;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum StatusPedido {

    RECEBIDO("Pedido recebido"),
    EM_PREPARO("Pedido em preparo"),
    PRONTO("Pedido pronto"),
    EM_ROTA("Pedido em rota"),
    ENTREGUE("Pedido entregue");

    private String valor;

    StatusPedido(String valor){
        this.valor = valor;
    }

}
