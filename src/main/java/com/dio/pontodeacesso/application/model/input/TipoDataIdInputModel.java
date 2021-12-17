package com.dio.pontodeacesso.application.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TipoDataIdInputModel {

    @NotNull
    private Long id_tipoData;
}
