package com.dio.pontodeacesso.application.model.input;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UsuarioIdInputModel {

    @NotNull
    @JsonValue
    private Long id_Usuario;
}
