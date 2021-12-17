package com.dio.pontodeacesso.application.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class LocalidadeModel {


    private LocalidadeIdOutput localidadeId;

    private String descricao;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class LocalidadeIdOutput implements Serializable {
        private static final long serialVersionUID = 1110147895524007386L;

        private long id;

        private long id_NivelAcesso;
    }
}
