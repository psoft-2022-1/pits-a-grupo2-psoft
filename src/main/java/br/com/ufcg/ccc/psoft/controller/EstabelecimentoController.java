package br.com.ufcg.ccc.psoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.service.EstabelecimentoService;
import br.com.ufcg.ccc.psoft.util.ErroEstabelecimento;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @PostMapping(value = "/estabelecimento/")
    public ResponseEntity<?> criarEstabelecimento(@RequestBody String codigoAcesso) {
            EstabelecimentoDTO estabelecimento = estabelecimentoService.criarEstabelecimento(codigoAcesso);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
    }

    @PutMapping(value = "/estabelecimento/{id}")
    public ResponseEntity<?> editarEstabelecimento(@PathVariable("id") Long idEstabelecimento, @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        try {
            EstabelecimentoDTO estabelecimento = estabelecimentoService.editarEstabelecimento(idEstabelecimento, estabelecimentoDTO);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e){
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
        }
    }
}
