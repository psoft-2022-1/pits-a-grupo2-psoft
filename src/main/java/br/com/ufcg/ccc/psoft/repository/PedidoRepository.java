package br.com.ufcg.ccc.psoft.repository;

import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ufcg.ccc.psoft.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    PedidoResponseDTO findPedidoByClienteAndId(Cliente cliente, Long idPedido);

    List<PedidoResponseDTO> findPedidosByClienteOrderByIdDesc(Cliente cliente);

    List<PedidoResponseDTO> findPedidosByClienteAndStatusPedidoOrderByIdDesc(Cliente cliente, String status);

}
