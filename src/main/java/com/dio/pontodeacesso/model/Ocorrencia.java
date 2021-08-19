package com.dio.pontodeacesso.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Ocorrencia;
    private String nome;
    private String descricao;
    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacao;

}
