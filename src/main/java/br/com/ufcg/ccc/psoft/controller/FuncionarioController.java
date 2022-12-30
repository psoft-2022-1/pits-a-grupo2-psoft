package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.requests.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.EntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.FuncionarioRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.FuncionarioResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.service.FuncionarioService;
import br.com.ufcg.ccc.psoft.service.util.ErroEntregador;
import br.com.ufcg.ccc.psoft.service.util.ErroEstabelecimento;
import br.com.ufcg.ccc.psoft.service.util.ErroFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping(value = "/funcionario/")
    public ResponseEntity<?> criarFuncionario(@RequestBody FuncionarioRequestDTO funcionarioRequestDTO){

        try {
            FuncionarioResponseDTO funcionario = funcionarioService.criaFuncionario(funcionarioRequestDTO);
            return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
        } catch (FuncionarioAlreadyCreatedException e) {
            return ErroFuncionario.erroFuncionarioJaCadastrado(funcionarioRequestDTO);
        } catch (InvalidCodigoAcessoException e) {
            return ErroFuncionario.erroCodigoAcessoInvalido();
        }
    }

    @DeleteMapping(value = "/funcionario/{id}")
    public ResponseEntity<?> removerFuncionario(@PathVariable("id") Long id) throws FuncionarioNotFoundException {

        try {
            funcionarioService.removerFuncionarioCadastrado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FuncionarioNotFoundException e) {
            return ErroFuncionario.erroFuncionarioNaoEncontrado();
        }
    }
    @PutMapping(value = "/estabelecimento/funcionario/avaliar-entregador/")
    public ResponseEntity<?> avaliarEntregador(@RequestBody AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) {
        try {
            EntregadorRequestDTO entregadorRequestDTO = funcionarioService.analisarEntregador(analisarEntregadorRequestDTO);
            return new ResponseEntity<>(entregadorRequestDTO, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FuncionarioNotFoundException e) {
            return ErroFuncionario.erroFuncionarioNaoEncontrado();
        } catch (IncorretCodigoAcessoException e) {
            return ErroEstabelecimento.erroCodigoAcessoIncorreto();
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(analisarEntregadorRequestDTO.getIdEntregador());
        }
    }
}
