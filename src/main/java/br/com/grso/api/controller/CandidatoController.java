package br.com.grso.api.controller;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.exception.CreationException;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/candidato")
@AllArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CandidatoController {

	private final CandidatoService candidatoService;

	//TODO melhorar retorno da chamada ao método salvar
	@PostMapping
	public ResponseEntity<Object> salvar(@RequestBody CandidatoDTO candidato) {
		CandidatoDTO candidatoDTO = null;
		try {
			candidatoDTO = candidatoService.salvar(candidato);
			return new ResponseEntity<>(candidatoDTO, HttpStatus.CREATED);

		} catch (CreationException e) {
			return new ResponseEntity<>(e.getMensagem(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/listar")
	public ResponseEntity<List<CandidatoDTO>> listar() {
		List<CandidatoDTO> candidatos = candidatoService.listar();
		return new ResponseEntity<>(candidatos, HttpStatus.OK);
	}

	@GetMapping(value = "/consulta-situacao/{cpf}/{senha}")
	public ResponseEntity<CandidatoDTO> consultar(@PathVariable String cpf, @PathVariable String senha) {
		CandidatoDTO candidato = candidatoService.consultar(cpf, senha);
		return new ResponseEntity<>(candidato, HttpStatus.OK);
	}

	@GetMapping(value = "/aprovar/{idCandidato}")
	public ResponseEntity<CandidatoDTO> aprovar(@PathVariable Long idCandidato) {
		CandidatoDTO candidatoDTO = candidatoService.aprovar(idCandidato);
		return new ResponseEntity<>(candidatoDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/reprovar/{idCandidato}")
	public ResponseEntity<CandidatoDTO> reprovar(@PathVariable Long idCandidato) {
		CandidatoDTO candidatoDTO = candidatoService.reprovar(idCandidato);
		return new ResponseEntity<>(candidatoDTO, HttpStatus.OK);
	}
}
