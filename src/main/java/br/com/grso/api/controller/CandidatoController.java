package br.com.grso.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.service.CandidatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/candidato")
@AllArgsConstructor
@Tag(name = "Candidato Endpoints", description = "Endpoints para gerenciar dados dos candidatos")
public class CandidatoController {

	private final CandidatoService candidatoService;

	@PostMapping
	public ResponseEntity<CandidatoDTO> saveProduct(@RequestBody CandidatoDTO candidato) {
		CandidatoDTO candaidatoDTO = candidatoService.saveCandidato(candidato);
		return new ResponseEntity<>(candaidatoDTO, HttpStatus.CREATED);
	}

}
