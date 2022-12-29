package br.com.ufcg.ccc.psoft.repository;

import br.com.ufcg.ccc.psoft.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ufcg.ccc.psoft.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    Pedido findPedidoByClienteAndId(Cliente cliente, Long idPedido);

    //colocar status que andreza fizer
//    List<Pedido> findPedidoByClienteAndStatusAndOrderByIdDesc(Cliente cliente, String status);

}
