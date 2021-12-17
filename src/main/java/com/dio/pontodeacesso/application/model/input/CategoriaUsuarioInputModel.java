package com.dio.pontodeacesso.application.model.input;


import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CategoriaUsuarioInputModel {

    @NotBlank
    private String descricao;

}
