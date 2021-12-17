package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LocalidadeInputModel implements Serializable{

    @Valid
    @NotNull
    private LocalidadeId localidadeId;

    @NotBlank
    private String descricao;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class LocalidadeId implements Serializable {

        @NotNull
        private Long id;
        @NotNull
        private Long id_NivelAcesso;
    }
}
