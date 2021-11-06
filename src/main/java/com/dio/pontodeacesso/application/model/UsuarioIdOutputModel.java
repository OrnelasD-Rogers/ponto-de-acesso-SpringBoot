package com.dio.pontodeacesso.application.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioIdOutputModel {

    @JsonValue
    private Long id_Usuario;

}
