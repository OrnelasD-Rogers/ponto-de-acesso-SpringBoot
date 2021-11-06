package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OcorrenciaInputModel {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
}
