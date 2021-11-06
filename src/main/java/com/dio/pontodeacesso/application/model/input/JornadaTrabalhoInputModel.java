package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter @Setter
public class JornadaTrabalhoInputModel {

    @NotBlank
    private String descricao;

}
