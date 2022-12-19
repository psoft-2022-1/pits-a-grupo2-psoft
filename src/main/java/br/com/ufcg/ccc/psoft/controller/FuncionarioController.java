package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.*;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.service.EstabelecimentoService;
import br.com.ufcg.ccc.psoft.service.FuncionarioService;
import br.com.ufcg.ccc.psoft.util.ErroCliente;
import br.com.ufcg.ccc.psoft.util.ErroFuncionario;
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
    public ResponseEntity<?> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) throws FuncionarioAlreadyCreatedException {

        try {
            FuncionarioDTO funcionario = funcionarioService.criaFuncionario(funcionarioDTO);
            return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
        } catch (FuncionarioAlreadyCreatedException e) {
            return ErroFuncionario.erroFuncionarioJaCadastrado(funcionarioDTO);
        }
    }
    @PutMapping(value = "/avaliar-entregador/")
    public ResponseEntity<?> avaliarEntregador(@RequestBody AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) {
        try {
            EntregadorDTO entregadorDTO = funcionarioService.analisarEntregador(analisarEntregadorRequestDTO);
            return new ResponseEntity<>(entregadorDTO, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FuncionarioNotFoundException e) {
            return ErroFuncionario.erroFuncionarioNaoEncontrado();
        } catch (IncorretCodigoAcessoException e) {
            throw new RuntimeException(e);
        } catch (EntregadorNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
