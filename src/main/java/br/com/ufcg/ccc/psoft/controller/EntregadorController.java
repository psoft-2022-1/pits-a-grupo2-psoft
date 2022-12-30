package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNaoAprovadoException;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.service.EntregadorService;
import br.com.ufcg.ccc.psoft.service.util.ErroEntregador;
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
        List<EntregadorResponseDTO> entregadores = entregadorService.listarEntregadores();
        if (entregadores.isEmpty()) {
            return ErroEntregador.erroSemEntregadoresCadastrados();
        }
        return new ResponseEntity<List<EntregadorResponseDTO>>(entregadores, HttpStatus.OK);
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
            EntregadorResponseDTO entregador = entregadorService.getEntregadorById(id);
            return new ResponseEntity<EntregadorResponseDTO>(entregador, HttpStatus.OK);
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

    @PutMapping(value = "/entregador/alteraStatus/{id}")
    public ResponseEntity<?> atualizaStatusDisponibilidade(@PathVariable("id") Long id, @RequestBody EntregadorRequestDTO entregadorRequestDTO) {
        try {
            return new ResponseEntity<>(this.entregadorService.atualizaStatusDisponibilidade(id, entregadorRequestDTO), HttpStatus.OK);
        } catch (EntregadorNaoAprovadoException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(id);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadoratualizaStatusDisponibilidade(id);
        } catch (IncorretCodigoAcessoException e) {
            return ErroEntregador.erroSenhaIncorreta();
        }
    }
}