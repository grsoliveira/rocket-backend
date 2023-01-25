package br.com.grso.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidato implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome_completo")
	private String nomeCompleto;

	private LocalDate nascimento;

	private String cpf;

	private String senha;

	@Column(name = "nome_completo_da_mae")
	private String nomeCompletoDaMae;

	@Column(name = "aprovado", columnDefinition = "boolean default null")
	private Boolean aprovado = false;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] foto;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] documento;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] comprovante;

}
