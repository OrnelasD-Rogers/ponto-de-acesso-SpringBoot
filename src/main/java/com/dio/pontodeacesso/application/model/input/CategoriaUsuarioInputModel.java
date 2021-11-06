package com.dio.pontodeacesso.application.model.input;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter @Setter
public class CategoriaUsuarioInputModel {

    @NotBlank
    private String descricao;

}
