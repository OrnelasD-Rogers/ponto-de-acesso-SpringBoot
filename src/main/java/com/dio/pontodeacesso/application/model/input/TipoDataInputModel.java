package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class TipoDataInputModel {

    @NotBlank
    private String descricao;
}
