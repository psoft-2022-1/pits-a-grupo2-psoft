package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.service.ClienteService;
import br.com.ufcg.ccc.psoft.service.util.ErroCliente;

import java.util.List;

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
		
		List<ClienteRequestDTO> clientes = clienteService.listaClientes();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<ClienteRequestDTO>>(clientes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criaCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {

		try {
			ClienteRequestDTO cliente = clienteService.criaCliente(clienteRequestDTO);
			return new ResponseEntity<ClienteRequestDTO>(cliente, HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteRequestDTO);
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
