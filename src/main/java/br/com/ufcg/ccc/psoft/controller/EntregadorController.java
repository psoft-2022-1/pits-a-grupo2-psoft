package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EntregadorStatusRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.EntregadorResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
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
            EntregadorResponseDTO entregador = entregadorService.criaEntregador(entregadorRequestDTO);
            return new ResponseEntity<EntregadorResponseDTO>(entregador, HttpStatus.CREATED);
        } catch (EntregadorAlreadyCreatedException e) {
            return ErroEntregador.erroEntregadorJaCadastrado(entregadorRequestDTO);
        } catch (InvalidCodigoAcessoException e) {
            return ErroEntregador.erroCodigoAcessoInvalido();
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

    @DeleteMapping(value = "/entregador/{idEntregador}")
    public ResponseEntity<?> removerEntregador(@PathVariable("idEntregador") long idEntregador) {

        try {
            entregadorService.removerEntregadorCadastrado(idEntregador);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        }
    }

    @GetMapping(value = "/entregador/{idEntregador}")
    public ResponseEntity<?> consultarEntregador(@PathVariable("idEntregador") long idEntregador) {

        try {
            EntregadorResponseDTO entregador = entregadorService.getEntregadorById(idEntregador);
            return new ResponseEntity<EntregadorResponseDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        }
    }

    @PutMapping(value = "/entregador/{idEntregador}")
    public ResponseEntity<?> atualizarEntregador(@PathVariable("idEntregador") long idEntregador, @RequestBody EntregadorRequestDTO entregadorRequestDTO) {

        try {
            EntregadorRequestDTO entregador = entregadorService.atualizaEntregador(idEntregador, entregadorRequestDTO);
            return new ResponseEntity<EntregadorRequestDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        }
    }

    @PutMapping(value = "/entregador/alteraStatus/{idEntregador}")
    public ResponseEntity<?> atualizaStatusDisponibilidade(@PathVariable("idEntregador") Long idEntregador, @RequestBody EntregadorStatusRequestDTO entregadorRequestDTO) {
        try {
            return new ResponseEntity<>(this.entregadorService.atualizaStatusDisponibilidade(idEntregador, entregadorRequestDTO), HttpStatus.OK);
        } catch (EntregadorNaoAprovadoException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadoratualizaStatusDisponibilidade(idEntregador);
        } catch (IncorretCodigoAcessoException e) {
            return ErroEntregador.erroSenhaIncorreta(entregadorRequestDTO.getCodigoAcesso());
        }
        
    }
}