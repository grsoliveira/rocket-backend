package br.com.grso.common.mappers;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.model.Candidato;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

@Component
public class CandidatoModelMapper implements ModelMapper<Candidato, CandidatoDTO> {

    @Override
    public CandidatoDTO entityToDto(Candidato entity) {
        CandidatoDTO dto = new CandidatoDTO();
        dto.setId(entity.getId());
        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setNascimento(entity.getNascimento());
        dto.setCpf(entity.getCpf());
        dto.setSenha(null);
        dto.setNomeCompletoDaMae(entity.getNomeCompletoDaMae());
        dto.setAprovado(entity.getAprovado());

        if (entity.getFoto() != null) {
            dto.setFoto(Base64.getEncoder().encodeToString(entity.getFoto()));
        }
        if (entity.getDocumento() != null) {
            dto.setDocumento(Base64.getEncoder().encodeToString(entity.getDocumento()));
        }
        if (entity.getComprovante() != null) {
            dto.setComprovante(Base64.getEncoder().encodeToString(entity.getComprovante()));
        }

        return dto;
    }

    @Override
    public Candidato dtoToEntity(CandidatoDTO dto) {
        Candidato entity = new Candidato();
        entity.setId(dto.getId());
        entity.setNomeCompleto(dto.getNomeCompleto());
        entity.setNascimento(dto.getNascimento());
        entity.setCpf(dto.getCpf());
        entity.setSenha(dto.getSenha());
        entity.setNomeCompletoDaMae(dto.getNomeCompletoDaMae());
        entity.setAprovado(dto.getAprovado());

        if (dto.getFoto() != null) {
            byte[] bytesFoto = dto.getFoto().substring(dto.getFoto().indexOf(",") + 1).getBytes(StandardCharsets.UTF_8);
            entity.setFoto( Base64.getDecoder().decode(bytesFoto) );
        }

        if (dto.getDocumento() != null) {
            byte[] bytesDocumento = dto.getDocumento().substring(dto.getDocumento().indexOf(",") + 1).getBytes(StandardCharsets.UTF_8);
            entity.setDocumento( Base64.getDecoder().decode(bytesDocumento) );
        }

        if (dto.getComprovante() != null) {
            byte[] bytesComprovante = dto.getComprovante().substring(dto.getComprovante().indexOf(",") + 1).getBytes(StandardCharsets.UTF_8);
            entity.setComprovante( Base64.getDecoder().decode(bytesComprovante) );
        }

        return entity;
    }
}
