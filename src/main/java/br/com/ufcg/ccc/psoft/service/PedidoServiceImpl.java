package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.PedidoNotFoundException;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import br.com.ufcg.ccc.psoft.model.Pedido;
import br.com.ufcg.ccc.psoft.model.Sabor;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    public ModelMapper modelMapper;

    public PedidoDTO criaPedido(PedidoDTO pedidoDTO)  {
        Pagamento pagamento = new Pagamento(pedidoDTO.getPagamento().getTipo(), pedidoDTO.getPagamento().getValor());
        for (ItemDePedido itemEscolhido : pedidoDTO.getItensEscolhidos()) {
            if (itemEscolhido.getTamanho().toUpperCase().equals("GRANDE") && itemEscolhido.getSabores().size()<=2) {
                List<Sabor> sabores = itemEscolhido.getSabores();
                ItemDePedido itemDePedido = new ItemDePedido(sabores, itemEscolhido.getTamanho(), itemEscolhido.getValor());
            } else {
                
            }
        }

        Pedido pedido = new Pedido(pedidoDTO.getCliente(), pedidoDTO.getItensEscolhidos(), pagamento, pedidoDTO.getEnderecoEntrega());
        salvarPedidoCadastrado(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }

    private void salvarPedidoCadastrado(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        pedidoRepository.delete(pedido);
    }

    private Pedido getPedidoId(Long id) throws PedidoNotFoundException {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException());
    }

    public PedidoDTO atualizarPedido (Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);

        pedido.setItensEscolhidos(pedidoDTO.getItensEscolhidos());
        pedido.setPagamento(pedidoDTO.getPagamento());
        pedido.setEnderecoEntrega(pedidoDTO.getEnderecoEntrega());

        this.pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
