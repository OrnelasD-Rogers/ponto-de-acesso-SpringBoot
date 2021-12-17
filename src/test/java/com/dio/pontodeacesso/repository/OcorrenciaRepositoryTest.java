package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.util.OcorrenciaCreator;
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
@DisplayName("Testa o repositório de Ocorrencia para o banco MySql")
class OcorrenciaRepositoryTest extends MySqlTest {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Test
    @DisplayName("Testa a inserção de uma Ocorrencia valida")
    void save_PersistOcorrencia_WhenSuccessful(){
        Ocorrencia ocorrenciaToBeSaved = OcorrenciaCreator.createValidOcorrencia();

        Ocorrencia ocorrenciaSaved = this.ocorrenciaRepository.save(ocorrenciaToBeSaved);

        assertThat(ocorrenciaSaved).isNotNull();

        assertThat(ocorrenciaSaved.getId_ocorrencia()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de Ocorrencia")
    void save_UpdateOcorrencia_WhenSuccessful(){
        Ocorrencia ocorrenciaToBeSaved = OcorrenciaCreator.createValidOcorrencia();

        Ocorrencia ocorrenciaSaved = this.ocorrenciaRepository.save(ocorrenciaToBeSaved);

        String desc = "Descricao de atualizacao";

        ocorrenciaSaved.setDescricao(desc);

        Ocorrencia ocorrenciaUpdated = this.ocorrenciaRepository.save(ocorrenciaSaved);

        assertThat(ocorrenciaUpdated).isNotNull();

        assertThat(ocorrenciaUpdated.getDescricao()).isEqualTo(desc);
    }

    @Test
    @DisplayName("Delete remove uma Ocorrencia quando realizado com sucesso")
    void delete_RemoveOcorrencia_WhenSuccessful(){
        Ocorrencia ocorrenciaToBeSaved = OcorrenciaCreator.createValidOcorrencia();

        Ocorrencia ocorrenciaSaved = this.ocorrenciaRepository.save(ocorrenciaToBeSaved);

        this.ocorrenciaRepository.delete(ocorrenciaSaved);

        Optional<Ocorrencia> ocorrenciaOptional = this.ocorrenciaRepository
                .findById(ocorrenciaSaved.getId_ocorrencia());

        assertThat(ocorrenciaOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir uma Ocorrencia em uso lança DataIntegrityViolationException")
    void deleteById_OcorrenciaThrowsDataIntegrityViolationException_WhenOcorrenciaIsInUse(){
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.ocorrenciaRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir uma Ocorrencia por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_OcorrenciaThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.ocorrenciaRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhuma Ocorrencia é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<Ocorrencia> ocorrenciaList = this.ocorrenciaRepository.findAll();

        Assertions.assertThat(ocorrenciaList).isEmpty();
    }



}