package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JornadaTrabalhoInputModel {

    @NotBlank
    private String descricao;

}
