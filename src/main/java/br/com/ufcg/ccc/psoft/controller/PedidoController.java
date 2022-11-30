package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.PedidoNotFoundException;
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

    @PostMapping(value = "/pedido/")
    public ResponseEntity<?> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedido = pedidoService.criaPedido(pedidoDTO);
        return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/pedido/{id}")
    public ResponseEntity<?> removerPedido(@PathVariable("id") long id) {

        try {
            pedidoService.removerPedidoCadastrado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroProdutoNaoEncontrado(id);
        }
    }

    @PutMapping(value = "/pedido/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable("id") long id, @RequestBody PedidoDTO pedidoDTO){
        try{
            PedidoDTO pedido = pedidoService.atualizarPedido(id, pedidoDTO);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
