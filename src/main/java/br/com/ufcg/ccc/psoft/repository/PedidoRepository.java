package br.com.ufcg.ccc.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ufcg.ccc.psoft.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
