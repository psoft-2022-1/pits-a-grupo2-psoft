package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.service.EntregadorService;
import br.com.ufcg.ccc.psoft.util.ErroEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EntregadorController {

    @Autowired
    EntregadorService entregadorService;

    @PostMapping(value = "/entregador/")
    public ResponseEntity<?> criarEntregador(@RequestBody EntregadorRequestDTO entregadorRequestDTO) {
        try {
            EntregadorRequestDTO entregador = entregadorService.criaEntregador(entregadorRequestDTO);
            return new ResponseEntity<EntregadorRequestDTO>(entregador, HttpStatus.CREATED);
        } catch (EntregadorAlreadyCreatedException e) {
            return ErroEntregador.erroEntregadorJaCadastrado(entregadorRequestDTO);
        }
    }

    @GetMapping(value = "/entregadores")
    public ResponseEntity<?> listarEntregadores() {
        List<EntregadorRequestDTO> entregadores = entregadorService.listarEntregadores();
        if (entregadores.isEmpty()) {
            return ErroEntregador.erroSemEntregadoresCadastrados();
        }
        return new ResponseEntity<List<EntregadorRequestDTO>>(entregadores, HttpStatus.OK);
    }

    @DeleteMapping(value = "/entregador/{id}")
    public ResponseEntity<?> removerEntregador(@PathVariable("id") long id) {

        try {
            entregadorService.removerEntregadorCadastrado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        }
    }

    @GetMapping(value = "/entregador/{id}")
    public ResponseEntity<?> consultarEntregador(@PathVariable("id") long id) {

        try {
            EntregadorRequestDTO entregador = entregadorService.getEntregadorById(id);
            return new ResponseEntity<EntregadorRequestDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        }
    }

    @PutMapping(value = "/entregador/{id}")
    public ResponseEntity<?> atualizarEntregador(@PathVariable("id") long id, @RequestBody EntregadorRequestDTO entregadorRequestDTO) {

        try {
            EntregadorRequestDTO entregador = entregadorService.atualizaEntregador(id, entregadorRequestDTO);
            return new ResponseEntity<EntregadorRequestDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        }
    }
}