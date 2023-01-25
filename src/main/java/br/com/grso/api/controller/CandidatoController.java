package br.com.grso.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/candidato")
@AllArgsConstructor
public class CandidatoController {

	private final CandidatoService candidatoService;

}
