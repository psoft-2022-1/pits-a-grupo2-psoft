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
	
	@DeleteMapping(value = "/cliente/{id}")
	public ResponseEntity<?> removeCliente(@PathVariable("id") Long id) {
			
		try {
			clienteService.removeClienteCadastrado(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}
	}
	
	@GetMapping(value = "/clientes")
	public ResponseEntity<?> listaClientes() {
		
		List<ClienteResponseDTO> clientes = clienteService.listaClientes();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<ClienteResponseDTO>>(clientes, HttpStatus.OK);
	}

	@GetMapping(value = "/cliente/{id}/pedido/{idPedido}")
	public ResponseEntity<?> pedidoCliente(@PathVariable("idPedido") long idPedido, @PathVariable("id") long idCliente) {

		try {
			ClienteRequestDTO cliente = clienteService.getClienteById(idCliente);
			PedidoResponseDTO pedido = pedidoService.getPedidoByClienteById(cliente, idPedido);
			if (pedido == null) {
				return ErroPedido.erroPedidoClienteNaoEncontrado(cliente.getNomeCompleto(), idPedido);
			}
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idPedido);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/}")
	public ResponseEntity<?> historicoPedidosCliente(@PathVariable("idCliente") long idCliente ) {

		try {
			ClienteRequestDTO cliente = clienteService.getClienteById(idCliente);
			List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByCliente(cliente);
			if (pedidos.isEmpty()) {
				return ErroPedido.erroClientesSemPedidos(cliente.getNomeCompleto());
			}
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/status/{status}")
	public ResponseEntity<?> historicoPedidosClienteStatus(@PathVariable("idCliente") long idCliente, @PathVariable("status") String statusPedido ) {

		try {
			ClienteRequestDTO cliente = clienteService.getClienteById(idCliente);
			List<PedidoResponseDTO> pedidos = pedidoService.getPedidosByClienteByStatus(cliente, statusPedido);
			if (pedidos.isEmpty()) {
				return ErroPedido.erroClientesSemPedidosStatus(cliente.getNomeCompleto(), statusPedido);
			}
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}
	
	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criaCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {

		try {
			ClienteResponseDTO cliente = clienteService.criaCliente(clienteRequestDTO);
			return new ResponseEntity<>(cliente, HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteRequestDTO);
		} catch (InvalidCodigoAcessoException e) {
			return ErroCliente.erroCodigoAcessoInvalido();
		}
	}
	
	@PutMapping(value = "/cliente/{id}")
	public ResponseEntity<?> atualizaCliente(@PathVariable("id") long id, @RequestBody ClienteRequestDTO clienteRequestDTO) {

		try {
			ClienteRequestDTO cliente = clienteService.atualizaCliente(id, clienteRequestDTO);
			return new ResponseEntity<ClienteRequestDTO>(cliente, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}
	}

	
}
