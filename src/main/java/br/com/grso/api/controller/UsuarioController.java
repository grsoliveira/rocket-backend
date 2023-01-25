package br.com.grso.api.controller;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.common.dto.UsuarioDTO;
import br.com.grso.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioDTO dto) {
        UsuarioDTO usuarioDTO = service.salvar(dto);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);
    }

}
