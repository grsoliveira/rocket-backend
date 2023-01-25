package br.com.grso.common.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO implements Serializable {

	private Long id;

	private String nomeCompleto;

	private LocalDate nascimento;

	private String cpf;

	private String nomeCompletoDaMae;
}
