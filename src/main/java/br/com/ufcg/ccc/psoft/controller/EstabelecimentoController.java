package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.EstabelecimentoRequestDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.InvalidCodigoAcessoException;
import br.com.ufcg.ccc.psoft.service.EstabelecimentoService;
import br.com.ufcg.ccc.psoft.util.ErroEstabelecimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @PostMapping(value = "/estabelecimento/")
    public ResponseEntity<?> criarEstabelecimento(@RequestBody EstabelecimentoRequestDTO estabelecimentoDTO) throws InvalidCodigoAcessoException {
        try{
            EstabelecimentoRequestDTO estabelecimento = estabelecimentoService.criarEstabelecimento(estabelecimentoDTO);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
        } catch (InvalidCodigoAcessoException e){
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }

    }

    @PutMapping(value = "/estabelecimento/{id}")
    public ResponseEntity<?> editarEstabelecimento(@PathVariable("id") Long idEstabelecimento, @RequestBody EstabelecimentoRequestDTO estabelecimentoDTO) {
        try {
            EstabelecimentoRequestDTO estabelecimento = estabelecimentoService.editarEstabelecimento(idEstabelecimento, estabelecimentoDTO);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e){
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
        }
    }
}
