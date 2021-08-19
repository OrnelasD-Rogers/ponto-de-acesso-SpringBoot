package com.dio.pontodeacesso.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
//Pegar uma API de data para preencher automáticamente

@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
public class Calendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Calendario;
    private String descricao;
    private LocalDateTime dataEspecial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoData")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TipoData tipoData;

    @OneToMany(mappedBy = "calendario", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacao;

}
