package br.com.ufcg.ccc.psoft.controller;

import br.com.ufcg.ccc.psoft.dto.AnalisarEntregadorRequestDTO;
import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
import br.com.ufcg.ccc.psoft.dto.EstabelecimentoDTO;
import br.com.ufcg.ccc.psoft.dto.FuncionarioDTO;
import br.com.ufcg.ccc.psoft.exception.EntregadorNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.FuncionarioNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.service.EstabelecimentoService;
import br.com.ufcg.ccc.psoft.service.FuncionarioService;
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

    @PutMapping(value = "/avaliar-entregador/")
    public ResponseEntity<?> avaliarEntregador(@RequestBody AnalisarEntregadorRequestDTO analisarEntregadorRequestDTO) {
        try {
            EntregadorDTO entregadorDTO = funcionarioService.analisarEntregador(analisarEntregadorRequestDTO);
            return new ResponseEntity<>(entregadorDTO, HttpStatus.OK);
        } catch (EstabelecimentoNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FuncionarioNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IncorretCodigoAcessoException e) {
            throw new RuntimeException(e);
        } catch (EntregadorNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
