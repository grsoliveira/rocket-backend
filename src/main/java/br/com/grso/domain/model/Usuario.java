package br.com.grso.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name= "USERNAME", nullable = false, unique = true)
    private String username;

    @Column (name = "PASSWORD", nullable = false)
    private String password;
}
