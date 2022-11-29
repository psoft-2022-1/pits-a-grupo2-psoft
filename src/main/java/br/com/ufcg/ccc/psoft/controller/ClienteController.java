package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.service.ClienteService;
import br.com.ufcg.ccc.psoft.util.ErroCliente;

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
	public ResponseEntity<?> removerCliente(@PathVariable("id") Long id) {
			
		try {
			clienteService.removerClienteCadastrado(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}
	}
	
	@GetMapping(value = "/clientes")
	public ResponseEntity<?> listarClientes() {
		
		List<ClienteDTO> clientes = clienteService.listarClientes();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<ClienteDTO>>(clientes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criarCliente(@RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.criaCliente(clienteDTO);
			return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteDTO);
		}
	}
	
	@PutMapping(value = "/cliente/{id}")
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.atualizaCliente(id, clienteDTO);
			return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}
	}

	
}
