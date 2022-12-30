package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.responses.CardapioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.CardapioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.service.EstabelecimentoService;
import br.com.ufcg.ccc.psoft.util.ErroCardapio;
import br.com.ufcg.ccc.psoft.util.ErroEstabelecimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CardapioController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @GetMapping(value = "/estabelecimento/{id}/cardapio/{sabor}")
    public ResponseEntity<?> getCardapioPorSabor(@PathVariable("id") long id, @PathVariable("sabor") String sabor) {
        try {
            CardapioResponseDTO cardapio = estabelecimentoService.consultaCarcapioPorSabor(id, sabor);
            return new ResponseEntity<>(cardapio, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(id);
        } catch (CardapioNotFoundException e) {
            return ErroCardapio.erroCardapioNaoEncontrado(id);
        }
    }

    @GetMapping(value = "/estabelecimento/{id}/cardapio/")
    public ResponseEntity<?> getCardapio(@PathVariable("id") long id) {
        try {
            CardapioResponseDTO cardapio = estabelecimentoService.getCardapio(id);
            return new ResponseEntity<>(cardapio, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(id);
        }
    }
}
