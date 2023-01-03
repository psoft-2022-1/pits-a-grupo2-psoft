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

    @GetMapping(value = "/estabelecimento/{idEstabelecimento}/cardapio/{tipoSabor}")
    public ResponseEntity<?> getCardapioPorSabor(@PathVariable("idEstabelecimento") long idEstabelecimento, @PathVariable("tipoSabor") String tipoSabor) {
        try {
            CardapioResponseDTO cardapio = estabelecimentoService.consultaCarcapioPorSabor(idEstabelecimento, tipoSabor);
            return new ResponseEntity<>(cardapio, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
        } catch (CardapioNotFoundException e) {
            return ErroCardapio.erroCardapioNaoEncontrado(idEstabelecimento);
        }
    }

    @GetMapping(value = "/estabelecimento/{idEstabelecimento}/cardapio/")
    public ResponseEntity<?> getCardapio(@PathVariable("idEstabelecimento") long idEstabelecimento) {
        try {
            CardapioResponseDTO cardapio = estabelecimentoService.getCardapio(idEstabelecimento);
            return new ResponseEntity<>(cardapio, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
        }
    }
}