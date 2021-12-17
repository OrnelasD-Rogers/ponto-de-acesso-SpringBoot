package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class EmpresaIdInputModel {

    @NotNull
    private Long id_empresa;
}
