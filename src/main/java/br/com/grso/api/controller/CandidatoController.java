package br.com.grso.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;

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


}
