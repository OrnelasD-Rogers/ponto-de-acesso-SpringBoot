package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CalendarioInputModel {

    @NotBlank
    private String descricao;

    @NotNull
    private OffsetDateTime dataEspecial;

    @Valid
    @NotNull
    private TipoDataIdInputModel tipoData;


}
