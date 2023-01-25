package br.com.grso.api.controller;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/candidato")
@AllArgsConstructor
public class CandidatoController {

	private final CandidatoService candidatoService;

	@PostMapping
	public ResponseEntity<CandidatoDTO> salvar(@RequestBody CandidatoDTO candidato) {
		CandidatoDTO candidatoDTO = candidatoService.salvarCandidato(candidato);
		return new ResponseEntity<>(candidatoDTO, HttpStatus.CREATED);
	}

	@GetMapping(value = "/listar")
	public ResponseEntity<List<CandidatoDTO>> listar() {
		List<CandidatoDTO> candidatos = candidatoService.listar();
		return new ResponseEntity<>(candidatos, HttpStatus.OK);
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
