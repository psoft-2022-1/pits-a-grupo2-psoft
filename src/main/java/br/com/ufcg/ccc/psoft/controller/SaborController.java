package br.com.ufcg.ccc.psoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufcg.ccc.psoft.dto.SaborDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.service.SaborService;
import br.com.ufcg.ccc.psoft.util.ErroEstabelecimento;
import br.com.ufcg.ccc.psoft.util.ErroSabor;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SaborController {

	@Autowired
	private SaborService saborService;

	@PostMapping(value = "estabelecimento/{idEstabelecimento}/cardapio/sabor/")
	public ResponseEntity<?> criarSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@RequestBody SaborDTO saborDTO) {

		try {
			SaborDTO sabor = saborService.criarSabor(idEstabelecimento, saborDTO);
			return new ResponseEntity<SaborDTO>(sabor, HttpStatus.CREATED);
		} catch (SaborAlreadyCreatedException e) {
			return ErroSabor.erroSaborJaCadastrado(saborDTO);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEntregadorNaoEncontrado(idEstabelecimento);
		}
	}

	@GetMapping(value = "/sabor/{idSabor}")
	public ResponseEntity<?> consultarSabor(@PathVariable("idSabor") long idSabor) {

		try {
			SaborDTO sabor = saborService.getSaborById(idSabor);
			return new ResponseEntity<SaborDTO>(sabor, HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		}
	}

	@PutMapping(value = "/sabor/{idSabor}")
	public ResponseEntity<?> atualizarSabor(@PathVariable("idSabor") long idSabor, @RequestBody SaborDTO saborDTO) {

		try {
			SaborDTO sabor = saborService.atualizarSabor(idSabor, saborDTO);
			return new ResponseEntity<SaborDTO>(sabor, HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		}
	}

	@DeleteMapping(value = "/estabelecimento/{idEstabelecimento}/sabor/{idSabor}")
	public ResponseEntity<?> removerSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@PathVariable("idSabor") long idSabor) {

		try {
			saborService.removerSaborCadastrado(idEstabelecimento, idSabor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEntregadorNaoEncontrado(idEstabelecimento);
		}
	}
}
