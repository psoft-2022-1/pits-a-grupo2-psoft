package br.com.ufcg.ccc.psoft.controller;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.responses.SaborResponseDTO;
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

import br.com.ufcg.ccc.psoft.dto.requests.SaborRequestDTO;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.SaborAlreadyCreatedException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.service.SaborService;
import br.com.ufcg.ccc.psoft.service.util.ErroEstabelecimento;
import br.com.ufcg.ccc.psoft.service.util.ErroSabor;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SaborController {

	@Autowired
	private SaborService saborService;

	@PostMapping(value = "estabelecimento/{idEstabelecimento}/cardapio/sabor/")
	public ResponseEntity<?> criarSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@RequestBody SaborRequestDTO saborRequestDTO) {

		try {
			SaborRequestDTO sabor = saborService.criarSabor(idEstabelecimento, saborRequestDTO);
			return new ResponseEntity<SaborRequestDTO>(sabor, HttpStatus.CREATED);
		} catch (SaborAlreadyCreatedException e) {
			return ErroSabor.erroSaborJaCadastrado(saborRequestDTO);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
		}
	}

	@GetMapping(value = "estabelecimento/{idEstabelecimento}/cardapio/sabor/{idSabor}")
	public ResponseEntity<?> consultarSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@PathVariable("idSabor") long idSabor) {

		try {
			SaborResponseDTO sabor = saborService.getSaborById(idEstabelecimento, idSabor);
			return new ResponseEntity<SaborResponseDTO>(sabor, HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
		}
	}

	@PutMapping(value = "estabelecimento/{idEstabelecimento}/cardapio/sabor/{idSabor}")
	public ResponseEntity<?> atualizarSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@PathVariable("idSabor") long idSabor, @RequestBody SaborRequestDTO saborRequestDTO) {

		try {
			SaborRequestDTO sabor = saborService.atualizarSabor(idEstabelecimento, idSabor, saborRequestDTO);
			return new ResponseEntity<SaborRequestDTO>(sabor, HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
		}
	}

	@DeleteMapping(value = "/estabelecimento/{idEstabelecimento}/cardapio/sabor/{idSabor}")
	public ResponseEntity<?> removerSabor(@PathVariable("idEstabelecimento") long idEstabelecimento,
			@PathVariable("idSabor") long idSabor) {

		try {
			saborService.removerSaborCadastrado(idEstabelecimento, idSabor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SaborNotFoundException e) {
			return ErroSabor.erroSaborNaoEncontrado(idSabor);
		} catch (EstabelecimentoNotFoundException e2) {
			return ErroEstabelecimento.erroEstabelecimentoNaoEncontrado(idEstabelecimento);
		}
	}
	
	@GetMapping(value = "/estabelecimento/{idEstabelecimento}/cardapio/")
	public ResponseEntity<?> listarSabores() {

		List<SaborResponseDTO> sabores = saborService.listarSabores();
		if (sabores.isEmpty()) {
			return ErroSabor.erroSaborNaoEncontrado(0);
		}

		return new ResponseEntity<List<SaborResponseDTO>>(sabores, HttpStatus.OK);
	}
}
