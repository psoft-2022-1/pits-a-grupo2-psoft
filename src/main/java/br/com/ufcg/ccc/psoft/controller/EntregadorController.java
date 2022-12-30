package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.SenhaInvalidaException;
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
    public ResponseEntity<?> criarEntregador(@RequestBody EntregadorDTO entregadorDTO) {
        try {
            EntregadorDTO entregador = entregadorService.criaEntregador(entregadorDTO);
            return new ResponseEntity<>(entregador, HttpStatus.CREATED);
        } catch (EntregadorAlreadyCreatedException e) {
            return ErroEntregador.erroEntregadorJaCadastrado(entregadorDTO);
        } catch (SenhaInvalidaException e) {
            return ErroEntregador.erroSenhaInvalida();
        }
    }

    @GetMapping(value = "/entregadores")
    public ResponseEntity<?> listarEntregadores() {
        List<EntregadorDTO> entregadores = entregadorService.listarEntregadores();
        if (entregadores.isEmpty()) {
            return ErroEntregador.erroSemEntregadoresCadastrados();
        }
        return new ResponseEntity<List<EntregadorDTO>>(entregadores, HttpStatus.OK);
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
            EntregadorDTO entregador = entregadorService.getEntregadorById(id);
            return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        }
    }

    @PutMapping(value = "/entregador/{id}")
    public ResponseEntity<?> atualizarEntregador(@PathVariable("id") long id, @RequestBody EntregadorDTO entregadorDTO) {

        try {
            EntregadorDTO entregador = entregadorService.atualizaEntregador(id, entregadorDTO);
            return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        } catch (SenhaInvalidaException e) {
            return ErroEntregador.erroSenhaInvalida();
        } catch (IncorretCodigoAcessoException e) {
            return ErroEntregador.senhaIncorreta();
        }
    }


}