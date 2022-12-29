package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.senhaInvalidaException;
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
    public ResponseEntity<?> removeCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {

        try {
            ClienteDTO cliente = clienteService.removeClienteCadastrado(id, clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch (IncorretCodigoAcessoException e) {
            return ErroCliente.senhaIncorreta();
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

    @PostMapping(value = "/cliente/")
    public ResponseEntity<?> criaCliente(@RequestBody ClienteDTO clienteDTO) {

        try {
            ClienteDTO cliente = clienteService.criaCliente(clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (ClienteAlreadyCreatedException e) {
            return ErroCliente.erroClienteJaCadastrado(clienteDTO);
        } catch (senhaInvalidaException e) {
            return ErroCliente.erroSenhaInvalida();
        }
    }

    @PutMapping(value = "/cliente/{id}")
    public ResponseEntity<?> atualizaCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {

        try {
            ClienteDTO cliente = clienteService.atualizaCliente(id, clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch (IncorretCodigoAcessoException e) {
            return ErroCliente.senhaIncorreta();
        } catch (senhaInvalidaException e) {
            return ErroCliente.erroSenhaInvalida();
        }
    }
}