package br.com.grso.domain.service.impl;

import br.com.grso.api.security.PasswordHelper;
import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.model.Candidato;
import br.com.grso.domain.repository.CandidatoRepository;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private final ModelMapper modelMapper;
    private final CandidatoRepository repository;

    private String encriptarSenha(String senha) {
        String result = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            result = new String(messageDigest);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    @Override
    public CandidatoDTO salvarCandidato(CandidatoDTO dto) {
        CandidatoDTO result = null;
        if (this.getQuantidadeInscricoes(dto.getCpf()) < 2) {
            Candidato candidato = modelMapper.map(dto, Candidato.class);
            candidato.setSenha(this.encriptarSenha(candidato.getSenha()));
            this.repository.save(candidato);
            result = modelMapper.map(candidato, CandidatoDTO.class);
        }
        return result;
    }

    public CandidatoDTO consultar(String cpf, String senha) {
        final String senhaEncriptada = this.encriptarSenha(senha);
        Candidato candidato = this.repository.findByCpfAndSenha(cpf, senhaEncriptada);
        return modelMapper.map(candidato, CandidatoDTO.class);
    }

    @Override
    public List<CandidatoDTO> listar() {
        List<CandidatoDTO> result = new ArrayList<>();
        this.repository.findAll().forEach(c -> {
            result.add(this.modelMapper.map(c, CandidatoDTO.class));
        });
        return result;
    }

    private CandidatoDTO alterarStatus(Long idCandidato, Boolean status) {
        CandidatoDTO dto = null;
        Optional<Candidato> candidato = this.repository.findById(idCandidato);
        if (candidato.isPresent()) {
            candidato.get().setAprovado(status);
            Candidato candidatoSalvo = this.repository.saveAndFlush(candidato.get());
            dto = this.modelMapper.map(candidatoSalvo, CandidatoDTO.class);
        }
        return dto;
    }

    @Override
    public CandidatoDTO aprovar(Long idCandidato) {
        return this.alterarStatus(idCandidato, Boolean.TRUE);
    }

    @Override
    public CandidatoDTO reprovar(Long idCandidato) {
        return this.alterarStatus(idCandidato, Boolean.FALSE);
    }

    private Integer getQuantidadeInscricoes(String cpf) {
        return this.repository.getQuantidadeInscricoes(cpf);
    }

}
