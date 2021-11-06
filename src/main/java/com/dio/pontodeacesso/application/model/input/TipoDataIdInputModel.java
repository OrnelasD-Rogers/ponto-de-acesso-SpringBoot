package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class TipoDataIdInputModel {

    @NotNull
    private Long id_tipoData;
}
