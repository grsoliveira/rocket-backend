package br.com.grso.domain.service.impl;

import br.com.grso.api.security.PasswordHelper;
import br.com.grso.common.dto.UsuarioDTO;
import br.com.grso.domain.model.Usuario;
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
    private final PasswordHelper passwordHelper;

    @Override
    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario usuario = this.modelMapper.map(dto, Usuario.class);

        usuario.setPassword(this.passwordHelper.getEncoder().encode(usuario.getPassword()));

        usuario = this.repository.save(usuario);
        return this.modelMapper.map(usuario, UsuarioDTO.class);
    }
}
