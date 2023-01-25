package br.com.grso.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreationException extends Exception{

    private String mensagem;

}
