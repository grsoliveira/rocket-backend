package br.com.grso.api.controller;

import br.com.grso.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;


}
