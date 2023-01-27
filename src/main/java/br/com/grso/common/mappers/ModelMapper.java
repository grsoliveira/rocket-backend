package br.com.grso.common.mappers;

import org.springframework.stereotype.Component;

@Component
public interface ModelMapper<ENTITY, DTO> {

    DTO entityToDto(ENTITY entity);

    ENTITY dtoToEntity(DTO dto);
}
