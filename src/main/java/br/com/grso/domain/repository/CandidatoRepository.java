package br.com.grso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grso.domain.model.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>{

    @Query("SELECT COUNT(*) FROM Candidato candidato WHERE candidato.cpf LIKE :cpf")
    public Integer getQuantidadeInscricoes(String cpf);

    @Query("SELECT COUNT(*) FROM Candidato candidato WHERE candidato.cpf LIKE :cpf AND candidato.aprovado IS NOT NULL AND candidato.aprovado = false")
    public Integer getQuantidadeInscricoesRecusadas(String cpf);

    @Query("SELECT COUNT(*) FROM Candidato candidato WHERE candidato.cpf LIKE :cpf AND candidato.aprovado IS NULL")
    public Integer getInscricoesPendentes(String cpf);

    Candidato findByCpfAndSenha(String cpf, String senhaEncriptada);
}
