package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.senhaInvalidaException;
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
    public ResponseEntity<?> criarEstabelecimento(@RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        try {
            EstabelecimentoDTO estabelecimento = estabelecimentoService.criarEstabelecimento(estabelecimentoDTO);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
        } catch (senhaInvalidaException e) {
            return ErroEstabelecimento.erroSenhaInvalida();
        }
    }

    @PutMapping(value = "/estabelecimento/{id}")
    public ResponseEntity<?> editarEstabelecimento(@PathVariable("id") Long idEstabelecimento, @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        try {
            EstabelecimentoDTO estabelecimento = estabelecimentoService.editarEstabelecimento(idEstabelecimento, estabelecimentoDTO);
            return new ResponseEntity<>(estabelecimento, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
        } catch (IncorretCodigoAcessoException e) {
            return ErroEstabelecimento.senhaIncorreta();
        } catch (senhaInvalidaException e) {
            return ErroEstabelecimento.erroSenhaInvalida();
        }
    }
}
