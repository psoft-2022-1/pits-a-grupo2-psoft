package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.*;
import br.com.ufcg.ccc.psoft.model.Enum.StatusPedido;
import br.com.ufcg.ccc.psoft.repository.ClienteRepository;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;
import br.com.ufcg.ccc.psoft.util.ErroPedido;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ItemDePedidoService itemDePedidoService;

    @Autowired
    public ModelMapper modelMapper;

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException {
    private ClienteRepository clienteRepository;

    @Autowired
    public ModelMapper modelMapper;

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException, PagamentoInvalidException {
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

        Pagamento pagamento = this.setTipoPagamento(pedidoDTO.getTipoPagamento());
        Pedido pedido = new Pedido(cliente, itensDePedidos, pagamento, pedidoDTO.getEnderecoEntrega(), calculaTotalPedido(itensDePedidos));
        salvarPedidoCadastrado(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }

    private Pagamento setTipoPagamento(String tipoPagamento) throws PagamentoInvalidException {
        if (tipoPagamento.toUpperCase().equals("PIX")){
            return new Pix();
        }else if(tipoPagamento.toUpperCase().equals("CARTÃO DE CRÉDITO")){
            return new CartaoCredito();
        }else if(tipoPagamento.toUpperCase().equals("CARTÃO DE DÉBITO")){
            return new CartaoDebito();
        } else{
            throw new PagamentoInvalidException();
        }
    }

    private Double calculaTotalPedido(List<ItemDePedido> itensDePedidos) {
        double total = 0;
        for (ItemDePedido item : itensDePedidos) {
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

    public PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);

        pedido.setItensEscolhidos(pedidoDTO.getItensEscolhidos());
        pedido.setEnderecoEntrega(pedidoDTO.getEnderecoEntrega());
        pedido.getPagamento().setTipoPagamento(pedidoDTO.getTipoPagamento());
        pedido.getPagamento().setDesconto(calculaTotalPedido(pedidoDTO.getItensEscolhidos()));
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
    public List<Pedido> getPedidosByCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidosByClienteOrderByIdDesc(cliente);
    }

    @Override
    public List<Pedido> getPedidosByClienteByStatus(ClienteDTO clienteDTO, String status) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidosByClienteAndStatusOrderByIdDesc(cliente, status);
    }


    @Override
    public PedidoDTO confirmarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        pedido.setStatusPedido(StatusPedido.EM_PREPARO);
        pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Override
    public PedidoDTO finalizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        pedido.setStatusPedido(StatusPedido.PRONTO);
        pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
