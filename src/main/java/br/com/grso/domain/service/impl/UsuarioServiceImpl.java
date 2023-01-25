package br.com.grso.domain.service.impl;

import br.com.grso.domain.repository.UsuarioRepository;
import br.com.grso.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final ModelMapper modelMapper;
    private final UsuarioRepository repository;

}
