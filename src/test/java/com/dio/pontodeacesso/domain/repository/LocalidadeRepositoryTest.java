package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.exception.NivelDeAcessoNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de Localidade para o banco MySql")
class LocalidadeRepositoryTest extends MySqlTest {

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private NivelAcessoRepository nivelAcessoRepository;

    @Test
    @DisplayName("Testa a inserção de uma Localidade valida")
    void save_PersistLocalidade_WhenSuccessful(){
        Localidade localidadeToBeSaved = createValidLocalidade();

        Localidade localidadeSaved = this.localidadeRepository.save(localidadeToBeSaved);

        assertThat(localidadeSaved).isNotNull();

        assertThat(localidadeSaved.getLocalidadeId().getId_nivelAcesso()).isNotNull();

        assertThat(localidadeSaved.getLocalidadeId().getId()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de Localidade")
    void save_UpdateLocalidade_WhenSuccessful(){
        Localidade localidadeToBeSaved = createValidLocalidade();

        Localidade localidadeSaved = this.localidadeRepository.save(localidadeToBeSaved);

        String desc = "Descricao de atualizacao";

        localidadeSaved.setDescricao(desc);

        Localidade localidadeUpdated = this.localidadeRepository.save(localidadeSaved);

        assertThat(localidadeUpdated).isNotNull();

        assertThat(localidadeUpdated.getDescricao()).isEqualTo(desc);
    }

    @Test
    @DisplayName("Testa a inserção de uma Localidade com um NivelAcesso invalido")
    void save_ThrowNivelDeAcessoNaoEncontradoException_WhenPkNivelAcessoIsInvalid() {
        //Ao não encontrar um objeto NivelDeAcesso válido, é lançada uma exceção
        assertThatExceptionOfType(NivelDeAcessoNaoEncontradoException.class)
                .isThrownBy(this::createInvalidLocalidade_PK_NivelAcesso);
    }

    @Test
    @DisplayName("Delete remove uma Localidade quando realizado com sucesso")
    void delete_RemoveLocalidade_WhenSuccessful(){
        Localidade localidadeToBeSaved = createValidLocalidade();

        Localidade localidadeSaved = this.localidadeRepository.save(localidadeToBeSaved);

        this.localidadeRepository.delete(localidadeSaved);

        Optional<Localidade> localidadeOptional = this.localidadeRepository
                .findById(localidadeSaved.getLocalidadeId());

        assertThat(localidadeOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir uma Localidade por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_LocalidadeThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.localidadeRepository.deleteById(createLocalidadeId(10L, 10L)));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhuma Localidade é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<Localidade> localidadeList = this.localidadeRepository.findAll();

        Assertions.assertThat(localidadeList).isEmpty();
    }

    private Localidade.LocalidadeId createLocalidadeId(Long idLocalidade, Long idnivelAcesso){
        return Localidade.LocalidadeId.builder()
                .id(idLocalidade)
                .id_nivelAcesso(idnivelAcesso).build();
    }

    private Localidade createValidLocalidade(){
        //Pego um objeto nívelAcesso válido para o JPA poder persistir uma nova Localidade
        Long id = 1L;
        NivelAcesso nivelAcesso = this.nivelAcessoRepository.findById(id).orElseThrow(()-> new NivelDeAcessoNaoEncontradoException(id));
        return Localidade.builder()
                .localidadeId(Localidade.LocalidadeId.builder().id(10L).id_nivelAcesso(nivelAcesso.getId_nivelAcesso()).build())
                .descricao("Nova desc")
                .nivelAcesso(nivelAcesso)
                .build();
    }

    private Localidade createInvalidLocalidade_PK_NivelAcesso(){
        //Pego um objeto nívelAcesso inválido para o JPA tentar persistir uma nova Localidade
        Long id = 100L;
        NivelAcesso nivelAcesso = this.nivelAcessoRepository.findById(id).orElseThrow(()-> new NivelDeAcessoNaoEncontradoException(id));
        return Localidade.builder()
                .localidadeId(Localidade.LocalidadeId.builder().id(10L).id_nivelAcesso(nivelAcesso.getId_nivelAcesso()).build())
                .descricao("Nova desc")
                .nivelAcesso(nivelAcesso)
                .build();
    }

}