package com.dio.pontodeacesso.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

//Classe Interna para poder utilizar uma chave primária composta
@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
public class MovimentacaoId implements Serializable {
    private static final long serialVersionUID = 1110147895524007386L;

    private long id_Movimento;
    private long id_Usuario;
}
