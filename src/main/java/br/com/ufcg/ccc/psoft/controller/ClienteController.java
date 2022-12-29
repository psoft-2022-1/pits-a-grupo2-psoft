package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Pedido;
import br.com.ufcg.ccc.psoft.service.ClienteService;
import br.com.ufcg.ccc.psoft.service.PedidoService;
import br.com.ufcg.ccc.psoft.util.ErroCliente;

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
		
		List<ClienteDTO> clientes = clienteService.listaClientes();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<ClienteDTO>>(clientes, HttpStatus.OK);
	}

	@GetMapping(value = "/cliente/{id}/pedido/{idPedido}")
	public ResponseEntity<?> pedidoCliente(@PathVariable("idPedido") long idPedido, @PathVariable("id") long idCliente) {

		try {
			ClienteDTO cliente = clienteService.getClienteById(idCliente);
			Pedido pedido = pedidoService.getPedidoByClienteById(cliente, idPedido);
			if (pedido == null) {
				return ErroPedido.erroPedidoClienteNaoEncontrado(cliente.getNomeCompleto(), idPedido);
			}
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idPedido);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/}")
	public ResponseEntity<?> historicoPedidoCliente(@PathVariable("idCliente") long idCliente ) {

		try {
			ClienteDTO cliente = clienteService.getClienteById(idCliente);
			List<Pedido> pedidos = pedidoService.getPedidosByCliente(cliente);
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}

	@GetMapping(value = "/cliente/{idCliente}/historicoPedidos/status/{status}")
	public ResponseEntity<?> historicoPedidoClienteStatus(@PathVariable("idCliente") long idCliente, @PathVariable("status") String statusPedido ) {

		try {
			ClienteDTO cliente = clienteService.getClienteById(idCliente);
			List<Pedido> pedidos = pedidoService.getPedidosByClienteByStatus(cliente, statusPedido);
			return new ResponseEntity<>(pedidos, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(idCliente);
		}
	}
	
	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criaCliente(@RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.criaCliente(clienteDTO);
			return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteDTO);
		}
	}
	
	@PutMapping(value = "/cliente/{id}")
	public ResponseEntity<?> atualizaCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.atualizaCliente(id, clienteDTO);
			return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}
	}

	
}
