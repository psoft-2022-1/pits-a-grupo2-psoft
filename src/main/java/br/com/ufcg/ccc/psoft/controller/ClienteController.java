package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.ClienteResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Pedido;
import br.com.ufcg.ccc.psoft.service.ClienteService;
import br.com.ufcg.ccc.psoft.service.PedidoService;
import br.com.ufcg.ccc.psoft.util.ErroCliente;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.PedidoNaoPertenceAEsseClienteException;
import br.com.ufcg.ccc.psoft.exception.PedidoNotFoundException;

import java.util.List;

import br.com.ufcg.ccc.psoft.util.ErroPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	PedidoService pedidoService;
	
	@DeleteMapping(value = "/cliente/{idCliente}")
	public ResponseEntity<?> removerCliente(@PathVariable("idCliente") Long idCliente) {
			
		try {
			clienteService.removerClienteCadastrado(idCliente);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}
	
	@GetMapping(value = "/clientes")
	public ResponseEntity<?> listarClientes() {
		
		List<ClienteResponseDTO> clientes = clienteService.listarClientes();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<ClienteResponseDTO>>(clientes, HttpStatus.OK);
	}

	@GetMapping(value = "/cliente/{idCliente}/pedido/{idPedido}")
	public ResponseEntity<?> pedidoCliente(@PathVariable("idPedido") long idPedido, @PathVariable("idCliente") long idCliente) {

		try {
			PedidoResponseDTO pedido = pedidoService.getPedidoByClienteById(idCliente, idPedido);
			if (pedido == null) {
				return ErroPedido.erroPedidoClienteNaoEncontrado(idCliente, idPedido);
			}
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idPedido);
		} catch (PedidoNaoPertenceAEsseClienteException e) {
			return ErroPedido.pedidoNaoPertenceAEsseCliente(idPedido, idCliente);
		} catch (PedidoNotFoundException e) {
			return ErroPedido.erroPedidoNaoEncontrado(idPedido);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/")
	public ResponseEntity<?> historicoPedidosCliente(@PathVariable("idCliente") long idCliente ) {

		try {
			List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByCliente(idCliente);
			if (pedidos.isEmpty()) {
				return ErroPedido.erroClientesSemPedidos(idCliente);
			}
			return new ResponseEntity<List<PedidoResponseDTO>>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/status/{status}")
	public ResponseEntity<?> historicoPedidosClienteStatus(@PathVariable("idCliente") long idCliente, @PathVariable("status") String statusPedido ) {

		try {
			List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByClienteByStatus(idCliente, statusPedido);
			if (pedidos.isEmpty()) {
				return ErroPedido.erroClientesSemPedidosStatus(idCliente, statusPedido);
			}
			return new ResponseEntity<List<PedidoResponseDTO>>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}
	
	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {

		try {
			ClienteResponseDTO cliente = clienteService.criarCliente(clienteRequestDTO);
			return new ResponseEntity<ClienteResponseDTO>(cliente, HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteRequestDTO);
		} catch (InvalidCodigoAcessoException e) {
			return ErroCliente.erroCodigoAcessoInvalido();
		}
	}
	
	@PutMapping(value = "/cliente/{idCliente}")
	public ResponseEntity<?> atualizarCliente(@PathVariable("idCliente") long idCliente, @RequestBody ClienteRequestDTO clienteRequestDTO) {

		try {
			ClienteResponseDTO cliente = clienteService.atualizarCliente(idCliente, clienteRequestDTO);
			return new ResponseEntity<ClienteResponseDTO>(cliente, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}

	
}