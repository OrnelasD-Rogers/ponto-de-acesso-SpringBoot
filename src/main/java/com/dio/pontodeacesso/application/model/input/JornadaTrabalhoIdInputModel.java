package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class JornadaTrabalhoIdInputModel {


    @NotNull
    private Long id_jornada_trabalho;
}
