package br.com.ufcg.ccc.psoft.repository;

import br.com.ufcg.ccc.psoft.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ufcg.ccc.psoft.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    Pedido findPedidoByClienteAndId(Cliente cliente, Long idPedido);

    List<Pedido> findPedidosByClienteOrderByIdDesc(Cliente cliente);

    List<Pedido> findPedidosByClienteAndStatusOrderByIdDesc(Cliente cliente, String status);

}
