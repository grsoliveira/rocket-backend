package br.com.grso.domain.service;

import br.com.grso.common.dto.CandidatoDTO;

import java.util.List;

public interface CandidatoService {

	CandidatoDTO salvarCandidato(CandidatoDTO candidato);

    List<CandidatoDTO> listar();

    CandidatoDTO aprovar(Long idCandidato);

    CandidatoDTO reprovar(Long idCandidato);
}
