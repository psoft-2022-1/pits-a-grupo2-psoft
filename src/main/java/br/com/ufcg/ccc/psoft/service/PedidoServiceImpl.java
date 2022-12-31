package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.*;
import br.com.ufcg.ccc.psoft.model.Enum.StatusPedido;
import br.com.ufcg.ccc.psoft.repository.EntregadorRepository;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;
import br.com.ufcg.ccc.psoft.util.ErroPedido;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ItemDePedidoService itemDePedidoService;

    @Autowired
    public ModelMapper modelMapper;

    public PedidoResponseDTO criaPedido(Long idCliente, PedidoRequestDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException, PagamentoInvalidException {
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

        return modelMapper.map(pedido, PedidoResponseDTO.class);
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

    public PedidoResponseDTO atualizarPedido (Long id, PedidoRequestDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);

        pedido.setItensEscolhidos(pedidoDTO.getItensEscolhidos());
        pedido.setEnderecoEntrega(pedidoDTO.getEnderecoEntrega());
        pedido.getPagamento().setTipoPagamento(pedidoDTO.getTipoPagamento());
        pedido.getPagamento().setDesconto(calculaTotalPedido(pedidoDTO.getItensEscolhidos()));
        this.pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }


    public PedidoResponseDTO getPedidoById(Long id) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO getPedidoByClienteById(ClienteRequestDTO clienteDTO, Long idPedido) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidoByClienteAndId(cliente, idPedido);
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByCliente(ClienteRequestDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidosByClienteOrderByIdDesc(cliente);
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByClienteByStatus(ClienteRequestDTO clienteDTO, String status) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return pedidoRepository.findPedidosByClienteAndStatusPedidoOrderByIdDesc(cliente, status);
    }


    @Override
    public PedidoResponseDTO confirmarPedido(Long id, PedidoRequestDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        pedido.setStatusPedido(StatusPedido.EM_PREPARO);
        pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO finalizarPedido(Long id, PedidoRequestDTO pedidoDTO) throws PedidoNotFoundException {
        Pedido pedido = getPedidoId(id);
        pedido.setStatusPedido(StatusPedido.PRONTO);

        Optional<Entregador> entregador = entregadorRepository.findByDisponibilidade("ATIVO");

        if(entregador.isPresent())
            pedido.setEntregador(entregador.get());

        pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }
    @Override
    public PedidoResponseDTO confirmaPedido(Long idPedido, Long idCliente) throws PedidoNotFoundException, PedidoNaoPertenceAEsseClienteException {
    	Pedido pedido = getPedidoId(idPedido);
    	if(!pedido.getCliente().getId().equals(idCliente)){
			 throw new PedidoNaoPertenceAEsseClienteException();
		}

    	pedido.setStatusDePedido("Pedido Entregue");
    	this.pedidoRepository.save(pedido);
    	return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    public void cancelaPedido(Long idPedido, Long idCliente) throws PedidoNotFoundException, PedidoJaEstaProntoException, PedidoNaoPertenceAEsseClienteException {
    		Pedido pedido = getPedidoId(idPedido);

    		if (pedido.getStatusDePedido().equals("Pedido pronto")) {
    			throw new PedidoJaEstaProntoException();
    		}

    			if(!pedido.getCliente().getId().equals(idCliente)){
    				 throw new PedidoNaoPertenceAEsseClienteException();
    			}

    			pedidoRepository.delete(pedido);

    }
}
