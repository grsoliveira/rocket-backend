package br.com.grso.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO implements Serializable {

    private Long id;
    private String nomeCompleto;
    private LocalDate nascimento;
    private String cpf;
    private String senha;
    private String nomeCompletoDaMae;
    private Boolean aprovado = false;
    private byte[] foto;
    private byte[] documento;
    private byte[] comprovante;
}
