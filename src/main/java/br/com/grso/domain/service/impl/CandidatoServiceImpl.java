package br.com.grso.domain.service.impl;

import br.com.grso.api.security.PasswordHelper;
import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.common.mappers.CandidatoModelMapper;
import br.com.grso.domain.exception.CreationException;
import br.com.grso.domain.model.Candidato;
import br.com.grso.domain.repository.CandidatoRepository;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoModelMapper candidatoModelMapper;
    private final CandidatoRepository repository;

    private String encriptarSenha(String senha) {
        String result = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            result = new String(messageDigest);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public CandidatoDTO salvar(CandidatoDTO dto) throws CreationException {
        CandidatoDTO result = null;

        dto.setId(null);
        dto.setAprovado(null);

        if (this.repository.getInscricoesPendentes(dto.getCpf()) > 0) {
            throw new CreationException("Candidato possui inscricao pendente de avaliação.");
        } else {
            if (this.repository.getQuantidadeInscricoesRecusadas(dto.getCpf()) >= 2) {
                throw new CreationException("Candidato já possui dois cadastros realizados.");
            } else {
                Candidato candidato = this.candidatoModelMapper.dtoToEntity(dto);

                candidato.setSenha(this.encriptarSenha(candidato.getSenha()));
                this.repository.save(candidato);
                result = this.candidatoModelMapper.entityToDto(candidato);
            }
        }

        return result;
    }

    public CandidatoDTO consultar(String cpf, String senha) {
        final String senhaEncriptada = this.encriptarSenha(senha);
        Candidato candidato = this.repository.findByCpfAndSenha(cpf, senhaEncriptada);
        return this.candidatoModelMapper.entityToDto(candidato);
    }

    @Override
    public List<CandidatoDTO> listar() {
        List<CandidatoDTO> result = new ArrayList<>();
        this.repository.findAll().forEach(c -> {
            result.add(this.candidatoModelMapper.entityToDto(c));
        });
        return result;
    }

    private CandidatoDTO alterarStatus(Long idCandidato, Boolean status) {
        CandidatoDTO dto = null;
        Optional<Candidato> candidato = this.repository.findById(idCandidato);
        if (candidato.isPresent()) {
            candidato.get().setAprovado(status);
            Candidato candidatoSalvo = this.repository.saveAndFlush(candidato.get());
            dto = this.candidatoModelMapper.entityToDto(candidatoSalvo);
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
