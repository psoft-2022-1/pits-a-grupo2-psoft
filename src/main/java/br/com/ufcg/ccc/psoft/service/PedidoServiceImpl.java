package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.*;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private SaborService saborService;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    public ModelMapper modelMapper;

    public PedidoDTO criaPedido(PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException {
        List<ItemDePedido> itensDePedidos = new ArrayList<>();
        for (ItemDePedido itemEscolhido : pedidoDTO.getItensEscolhidos()) {
            Double valor = 0.00;
            List<Sabor> saboresEscolhidos = new ArrayList<>();
            if (itemEscolhido.getTamanho().toUpperCase().equals("MEDIO") && itemEscolhido.getSabores().size()>1) {
                throw new QuantidadeSaboresInvalidosException();
            }
            if (itemEscolhido.getTamanho().toUpperCase().equals("GRANDE") && itemEscolhido.getSabores().size()<=2) {
                for (Sabor sabor : itemEscolhido.getSabores()) {
                    if (saborService.getSaborById(sabor.getId()) == null) {
                        throw new SaborNotFoundException();
                    }
                    valor += sabor.getValorGrande();
                    saboresEscolhidos.add(sabor);
                }
                valor = valor/2;
            } else if (itemEscolhido.getTamanho().toUpperCase().equals("MEDIO") && itemEscolhido.getSabores().size()==1){
                saboresEscolhidos.add(itemEscolhido.getSabores().get(0));
                valor = itemEscolhido.getSabores().get(0).getValorMedio();
            }
            ItemDePedido itemDePedido = new ItemDePedido(saboresEscolhidos, itemEscolhido.getTamanho(), valor);
            itensDePedidos.add(itemDePedido);
            saboresEscolhidos.clear();
        }
        if (pedidoDTO.getEnderecoEntrega().isBlank()) {
            pedidoDTO.setEnderecoEntrega(pedidoDTO.getCliente().getEnderecoPrincipal());
        }

        Pagamento pagamento = new Pagamento(pedidoDTO.getPagamento().getTipo(), pedidoDTO.getPagamento().getValor());
        ClienteDTO cliente = clienteService.getClienteById(pedidoDTO.getCliente().getId());
        if (!cliente.getCodAcesso().equals(pedidoDTO.getCliente().getCodAcesso())) {
            throw new IncorretCodigoAcessoException();
        }
        Pedido pedido = new Pedido(pedidoDTO.getCliente(), itensDePedidos, pagamento, pedidoDTO.getEnderecoEntrega(), pedidoDTO.getValorTotal());
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

    public PedidoDTO getPedidoById(Long id) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

}
