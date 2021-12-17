package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.*;
import com.dio.pontodeacesso.util.UsuarioCreator;
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
@DisplayName("Testa o repositório de Usuario para o banco MySql")
class UsuarioRepositoryTest extends MySqlTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Testa a inserção de um Usuario valido")
    void save_PersistUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved =  UsuarioCreator.createValidUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        assertThat(usuarioSaved).isNotNull();

        assertThat(usuarioSaved.getId_usuario()).isNotNull();
    }

    @Test
    @DisplayName("Testa a inserção de um Usuario com uma CategoriaUsuario invalida")
    void save_ThrowDataIntegrityViolationException_WhenFkCategoriaUsuarioIsInvalid(){
        Usuario usuarioToBeSaved = UsuarioCreator.createInvalidUsuario_FK_CategoriaUsuario();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.usuarioRepository.save(usuarioToBeSaved));
    }

    @Test
    @DisplayName("Testa a inserção de um Usuario com uma Empresa invalida")
    void save_ThrowDataIntegrityViolationException_WhenFkEmpresaIsInvalid(){
        Usuario usuarioToBeSaved = UsuarioCreator.createInvalidUsuario_FK_Empresa();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.usuarioRepository.save(usuarioToBeSaved));
    }

    @Test
    @DisplayName("Testa a inserção de um Usuario com uma JornadaTrabalho invalida")
    void save_ThrowDataIntegrityViolationException_WhenFkJornadaTrabalhoIsInvalid(){
        Usuario usuarioToBeSaved = UsuarioCreator.createInvalidUsuario_FK_JornadaTrabalho();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.usuarioRepository.save(usuarioToBeSaved));
    }

    @Test
    @DisplayName("Testa a inserção de um Usuario com um NivelAcesso invalido")
    void save_ThrowDataIntegrityViolationException_WhenFkNivelAcessoIsInvalid(){
        Usuario usuarioToBeSaved = UsuarioCreator.createInvalidUsuario_FK_NivelAcesso();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.usuarioRepository.save(usuarioToBeSaved));
    }

    @Test
    @DisplayName("Testa a atualização de Usuario")
    void save_UpdateUsuario_WhenSuccessful(){

        Usuario usuarioToBeSaved = UsuarioCreator.createValidUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        String nome = "Novo nome";

        usuarioSaved.setNome(nome);

        Usuario usuarioUpdated = this.usuarioRepository.save(usuarioSaved);

        Assertions.assertThat(usuarioSaved).isNotNull();

        Assertions.assertThat(usuarioSaved.getId_usuario()).isNotNull();

        Assertions.assertThat(usuarioUpdated.getNome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("Delete remove um Usuario quando realizado com sucesso")
    void delete_RemoveUsuario_WhenSuccessful(){

        Usuario usuarioToBeSaved = UsuarioCreator.createValidUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        this.usuarioRepository.delete(usuarioSaved);

        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuarioSaved.getId_usuario());

        Assertions.assertThat(usuarioOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um Usuario em uso lança DataIntegrityViolationException")
    void deleteById_UsuarioThrowsDataIntegrityViolationException_WhenUsuarioIsInUse(){

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.usuarioRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir um Usuario por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_UsuarioThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.usuarioRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhum Usuario é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<Usuario> usuarioList = this.usuarioRepository.findAll();

        Assertions.assertThat(usuarioList).isEmpty();
    }



}