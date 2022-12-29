package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.*;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;
import br.com.ufcg.ccc.psoft.util.ErroPedido;
import net.bytebuddy.asm.Advice;
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
    private ClienteService clienteService;

    @Autowired
    private ItemDePedidoService itemDePedidoService;

    @Autowired
    public ModelMapper modelMapper;

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException {
        List<ItemDePedido> itensDePedidos = new ArrayList<>();
        for (ItemDePedido itemDePedido : pedidoDTO.getItensEscolhidos()) {
            Double value = itemDePedidoService.checkItem(itemDePedido);
            ItemDePedido itemEscolhido = new ItemDePedido(itemDePedido.getSabores(), itemDePedido.getTamanho(), value);
            itensDePedidos.add(itemEscolhido);
        }

        Cliente cliente = clienteService.checkCodAcesso(idCliente, pedidoDTO.getCodCliente());

        if (pedidoDTO.getEnderecoEntrega().isBlank()) {
            pedidoDTO.setEnderecoEntrega(cliente.getEnderecoPrincipal());
        }

        Pagamento pagamento = new Pagamento(pedidoDTO.getTipoPagamento(), calculaTotalPedido(itensDePedidos));
        Pedido pedido = new Pedido(cliente, itensDePedidos, pagamento, pedidoDTO.getEnderecoEntrega(), calculaTotalPedido(itensDePedidos));
        salvarPedidoCadastrado(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }

    private Double calculaTotalPedido(List<ItemDePedido> itensDePedidos){
        double total = 0;
        for (ItemDePedido item : itensDePedidos){
            total += item.getValor();
        }
        return total;
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
        pedido.setEnderecoEntrega(pedidoDTO.getEnderecoEntrega());
        pedido.getPagamento().setTipo(pedidoDTO.getTipoPagamento());
        pedido.getPagamento().setValor(calculaTotalPedido(pedidoDTO.getItensEscolhidos()));
        this.pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }


    public PedidoDTO getPedidoById(Long id) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Override
    public Pedido getPedidoByClienteById(ClienteDTO clienteDTO, Long idPedido) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidoByClienteAndId(cliente, idPedido);
    }

    @Override
    public List<Pedido> getPedidosByClienteByStatus(ClienteDTO clienteDTO, String status) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
//        return pedidoRepository.findPedidoByClienteAndStatusAndOrderByIdDesc(cliente, status);
        return null;
    }


}
