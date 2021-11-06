package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;


@Getter @Setter
public class CalendarioInputModel {

    @NotBlank
    private String descricao;

    @NotNull
    private OffsetDateTime dataEspecial;

    @Valid
    private TipoDataIdInputModel tipoData;


}
