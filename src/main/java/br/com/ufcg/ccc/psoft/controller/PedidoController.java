package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.service.PedidoService;
import br.com.ufcg.ccc.psoft.util.ErroCliente;
import br.com.ufcg.ccc.psoft.util.ErroEntregador;
import br.com.ufcg.ccc.psoft.util.ErroEstabelecimento;
import br.com.ufcg.ccc.psoft.util.ErroPagamento;
import br.com.ufcg.ccc.psoft.util.ErroPedido;
import br.com.ufcg.ccc.psoft.util.ErroSabor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@PostMapping(value = "/pedido/cliente/{idCliente}")
	public ResponseEntity<?> criarPedido(@PathVariable Long idCliente, @RequestBody PedidoRequestDTO pedidoDTO) {
		PedidoResponseDTO pedido;

		try {
			pedido = pedidoService.criarPedido(idCliente, pedidoDTO);
			return new ResponseEntity<>(pedido, HttpStatus.CREATED);
		} catch (NomeDoSaborEIdNaoCorrespondemException e) {
			return ErroSabor.erroNomeEIdDeSaborNaoCorrespondem();
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroAlgumDosSaboresNaoEncontrado();
		} catch (QuantidadeSaboresInvalidosException e) {
			return ErroPedido.QuantidadeSaboresDoPedidoInvalido();
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		} catch (IncorretCodigoAcessoException e) {
			return ErroCliente.erroCodigoAcessoInvalido();
		} catch (PagamentoInvalidException e) {
			return ErroPagamento.erroPagamentoNaoValido(pedidoDTO.getTipoPagamento());
		} catch (EstabelecimentoNotFoundException e) {
			return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(pedidoDTO.getIdEstabelecimento());
		} catch (SaborNaoEstaDisponivelException e) {
			return ErroSabor.saborNaoEstaDisponivel();
		}

	}

	@DeleteMapping(value = "/pedido/{idPedido}")
	public ResponseEntity<?> removerPedido(@PathVariable("idPedido") long idPedido) {

		try {
			pedidoService.removerPedidoCadastrado(idPedido);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		}
	}

	@PutMapping(value = "cliente/{idCliente}/pedido/atualizar/{idPedido}")
	public ResponseEntity<?> atualizarPedido(@PathVariable("idCliente") long idCliente, @PathVariable("idPedido") long idPedido, @RequestBody PedidoRequestDTO pedidoDTO) {
		try {
			PedidoResponseDTO pedido = pedidoService.atualizarPedido(idCliente,idPedido, pedidoDTO);
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			throw new RuntimeException(e);
		} catch (QuantidadeSaboresInvalidosException e) {
			return ErroPedido.QuantidadeSaboresDoPedidoInvalido();
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroAlgumDosSaboresNaoEncontrado();
		} catch (NomeDoSaborEIdNaoCorrespondemException e) {
			return ErroSabor.erroNomeEIdDeSaborNaoCorrespondem();
		} catch (SaborNaoEstaDisponivelException e) {
			return ErroSabor.saborNaoEstaDisponivel();
		} catch (PagamentoInvalidException e) {
			return ErroPagamento.erroPagamentoNaoValido(pedidoDTO.getTipoPagamento());
		} catch (PedidoNaoPertenceAEsseClienteException e) {
			return ErroPedido.pedidoNaoPertenceAEsseCliente(idPedido, idCliente);
		}
	}

	@GetMapping(value = "pedido/{idPedido}")
	public ResponseEntity<?> consultarPedido(@PathVariable("idPedido") long idPedido) {

		try {
			PedidoResponseDTO pedido = pedidoService.getPedidoById(idPedido);
			return new ResponseEntity<PedidoResponseDTO>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		}
	}

	@PutMapping(value = "/pedido/confirmarPedido/{idPedido}")
	public ResponseEntity<?> confirmarPedido(@PathVariable("idPedido") long idPedido) {
		try {
			PedidoResponseDTO pedido = pedidoService.confirmarPedido(idPedido);
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		} catch (PedidoComStatusIncorretoParaMudancaException e) {
			return ErroPedido.pedidoComStatusIncorretoParaMudanca(idPedido);
		}
	}

	@PutMapping(value = "/pedido/finalizarPedido/{idPedido}")
	public ResponseEntity<?> finalizarPedido(@PathVariable("idPedido") long idPedido) {
		try {
			PedidoResponseDTO pedido = pedidoService.finalizarPedido(idPedido);
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		} catch (PedidoComStatusIncorretoParaMudancaException e) {
			return ErroPedido.pedidoComStatusIncorretoParaMudanca(idPedido);
		} catch (NaoHaEntregadoresDisponiveisException e) {
			return ErroEntregador.naoHaEntregadorDisponivel();
		}
	}

	@PutMapping(value = " cliente/{idCliente}/pedido/{idPedido}")
	public ResponseEntity<?> confirmarEntregaPedido(@PathVariable("idPedido") long idPedido,
			@PathVariable("idCliente") Long idCliente) {
		try {
			PedidoResponseDTO pedido = pedidoService.confirmarEntregaPedido(idPedido, idCliente);
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		} catch (PedidoNaoPertenceAEsseClienteException e) {

			return ErroPedido.pedidoNaoPertenceAEsseCliente(idPedido, idCliente);

		} catch (PedidoComStatusIncorretoParaMudancaException e) {
			return ErroPedido.pedidoComStatusIncorretoParaMudanca(idPedido);
		}
	}

	@DeleteMapping(value = "cliente/{idCliente}/pedido/{idPedido}")
	public ResponseEntity<?> cancelaPedido(@PathVariable("idPedido") long idPedido,
			@PathVariable("idCliente") Long idCliente) {

		try {
			pedidoService.cancelaPedido(idPedido, idCliente);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		} catch (PedidoJaEstaProntoException e) {
			return ErroPedido.pedidoJaEstaPronto(idPedido);
		} catch (PedidoNaoPertenceAEsseClienteException e) {
			return ErroPedido.pedidoNaoPertenceAEsseCliente(idPedido, idCliente);
		}
	}
}