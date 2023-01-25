package br.com.grso.common.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperFactory {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
