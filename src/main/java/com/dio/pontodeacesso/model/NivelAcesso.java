package com.dio.pontodeacesso.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
public class NivelAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_NivelAcesso;
    private String descricao;
    @OneToMany(mappedBy = "nivelAcesso", cascade = CascadeType.ALL)
    private List<Localidade> localidade;

    @OneToMany(mappedBy = "nivelAcesso", cascade = CascadeType.ALL)
    private List<Usuario> usuario;

}
