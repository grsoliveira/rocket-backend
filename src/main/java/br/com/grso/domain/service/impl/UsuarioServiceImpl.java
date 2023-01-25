package br.com.grso.domain.service.impl;

import br.com.grso.common.config.BCryptConfig;
import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.common.dto.UsuarioDTO;
import br.com.grso.domain.model.Candidato;
import br.com.grso.domain.model.Usuario;
import br.com.grso.domain.repository.CandidatoRepository;
import br.com.grso.domain.repository.UsuarioRepository;
import br.com.grso.domain.service.CandidatoService;
import br.com.grso.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final ModelMapper modelMapper;
    private final UsuarioRepository repository;
    private final BCryptConfig cryptConfig;

    @Override
    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        usuario.setPassword(cryptConfig.bCryptPasswordEncoder().encode(dto.getPassword()));
        this.repository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

}
