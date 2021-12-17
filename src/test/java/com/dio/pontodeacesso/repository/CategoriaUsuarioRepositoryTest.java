package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.util.CategoriaUsuarioCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de CategoriaUsuario para o banco MySql")
class CategoriaUsuarioRepositoryTest extends MySqlTest {

    @Autowired
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Test
    @DisplayName("Testa a inserção de uma CategoriaUsuario valida")
    void save_PersistCategoriaUsuario_WhenSuccessful(){
        CategoriaUsuario categoriaUsuarioToBeSaved = CategoriaUsuarioCreator.createValidCategoriaUsuario();

        CategoriaUsuario categoriaUsuarioSaved = this.categoriaUsuarioRepository.save(categoriaUsuarioToBeSaved);

        assertThat(categoriaUsuarioSaved).isNotNull();

        assertThat(categoriaUsuarioSaved.getId_cat_usuario()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de CategoriaUsuario")
    void save_UpdateCategoriaUsuario_WhenSuccessful(){
        CategoriaUsuario categoriaUsuarioToBeSaved = CategoriaUsuarioCreator.createValidCategoriaUsuario();

        CategoriaUsuario categoriaUsuarioSaved = this.categoriaUsuarioRepository.save(categoriaUsuarioToBeSaved);

        String desc = "Descricao de atualizacao";

        categoriaUsuarioSaved.setDescricao(desc);

        CategoriaUsuario categoriaUsuarioUpdated = this.categoriaUsuarioRepository.save(categoriaUsuarioSaved);

        assertThat(categoriaUsuarioUpdated).isNotNull();

        assertThat(categoriaUsuarioUpdated.getDescricao()).isEqualTo(desc);
    }

    @Test
    @DisplayName("Delete remove uma CategoriaUsuario quando realizado com sucesso")
    void delete_RemoveCategoriaUsuario_WhenSuccessful(){

        CategoriaUsuario categoriaUsuarioToBeSaved = CategoriaUsuarioCreator.createValidCategoriaUsuario();

        CategoriaUsuario categoriaUsuarioSaved = this.categoriaUsuarioRepository.save(categoriaUsuarioToBeSaved);

        this.categoriaUsuarioRepository.delete(categoriaUsuarioSaved);

        Optional<CategoriaUsuario> categoriaUsuarioOptional = this.categoriaUsuarioRepository
                .findById(categoriaUsuarioSaved.getId_cat_usuario());

        assertThat(categoriaUsuarioOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir uma CategoriaUsuario em uso lança DataIntegrityViolationException")
    void deleteById_CategoriaUsuarioThrowsDataIntegrityViolationException_WhenCategoriaUsuarioIsInUse(){
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.categoriaUsuarioRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir uma CategoriaUsuario por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_CategoriaUsuarioThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.categoriaUsuarioRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhuma CategoriaUsuario é encontrada")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<CategoriaUsuario> categoriaUsuarioList = this.categoriaUsuarioRepository.findAll();

        Assertions.assertThat(categoriaUsuarioList).isEmpty();
    }


}