package br.com.grso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grso.domain.model.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>{

}
