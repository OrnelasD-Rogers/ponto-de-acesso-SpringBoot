package com.dio.pontodeacesso.application.model.input;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UsuarioIdInputModel {

    @NotNull
    @JsonValue
    private Long id_Usuario;
}
