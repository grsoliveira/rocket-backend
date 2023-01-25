package br.com.grso.domain.service.impl;

import br.com.grso.domain.model.Candidato;
import br.com.grso.domain.repository.CandidatoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.grso.common.dto.CandidatoDTO;
import br.com.grso.domain.service.CandidatoService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

	private final ModelMapper modelMapper;
	private final CandidatoRepository repository;

	@Override
	public CandidatoDTO salvarCandidato(CandidatoDTO dto) {
		CandidatoDTO result = null;
		if (this.getQuantidadeInscricoes(dto.getCpf()) < 2) {
			Candidato candidato = modelMapper.map(dto, Candidato.class);
			this.repository.save(candidato);
			result = modelMapper.map(candidato, CandidatoDTO.class);
		}
		return result;
	}

	private Integer getQuantidadeInscricoes(String cpf) {
		return this.repository.getQuantidadeInscricoes(cpf);
	}

}
