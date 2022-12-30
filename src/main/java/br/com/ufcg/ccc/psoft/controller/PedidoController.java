package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.service.PedidoService;
import br.com.ufcg.ccc.psoft.util.ErroPedido;
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
    public ResponseEntity<?> criarPedido(@PathVariable Long idCliente, @RequestBody PedidoRequestDTO pedidoDTO) throws QuantidadeSaboresInvalidosException, SaborNotFoundException, IncorretCodigoAcessoException, ClienteNotFoundException, PagamentoInvalidException {
        PedidoResponseDTO pedido = pedidoService.criaPedido(idCliente, pedidoDTO);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/pedido/{id}")
    public ResponseEntity<?> removerPedido(@PathVariable("id") long id) {

        try {
            pedidoService.removerPedidoCadastrado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(id);
        }
    }

    @PutMapping(value = "/pedido/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable("id") long id, @RequestBody PedidoRequestDTO pedidoDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.atualizarPedido(id, pedidoDTO);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            throw new RuntimeException(e);
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

    @PutMapping(value = "/pedido/confirmarPedido/{id}")
    public ResponseEntity<?> confirmarPedido(@PathVariable("id") long id, @RequestBody PedidoRequestDTO pedidoDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.confirmarPedido(id, pedidoDTO);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/pedido/finalizarPedido/{id}")
    public ResponseEntity<?> finalizarPedido(@PathVariable("id") long id, @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.finalizarPedido(id, pedidoRequestDTO);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}