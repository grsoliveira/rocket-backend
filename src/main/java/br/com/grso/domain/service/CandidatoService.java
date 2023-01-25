package br.com.grso.domain.service;

import br.com.grso.common.dto.CandidatoDTO;

import java.util.List;

public interface CandidatoService {

	CandidatoDTO salvar(CandidatoDTO candidato);

    List<CandidatoDTO> listar();

    CandidatoDTO aprovar(Long idCandidato);

    CandidatoDTO reprovar(Long idCandidato);

    CandidatoDTO consultar(String cpf, String senha);
}
