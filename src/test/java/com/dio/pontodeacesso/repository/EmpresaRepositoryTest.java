package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.Empresa;
import com.dio.pontodeacesso.util.EmpresaCreator;
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
@DisplayName("Testa o repositório de Empresa para o banco MySql")
class EmpresaRepositoryTest extends MySqlTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Testa a inserção de uma Empresa valida")
    void save_PersistEmpresa_WhenSuccessful(){
        Empresa empresaToBeSaved = EmpresaCreator.createValidEmpresa();

        Empresa empresaSaved = this.empresaRepository.save(empresaToBeSaved);

        assertThat(empresaSaved).isNotNull();

        assertThat(empresaSaved.getId_empresa()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de Empresa")
    void save_UpdateEmpresa_WhenSuccessful(){
        Empresa empresaToBeSaved = EmpresaCreator.createValidEmpresa();

        Empresa empresaSaved = this.empresaRepository.save(empresaToBeSaved);

        String desc = "Descricao de atualizacao";

        empresaSaved.setDescricao(desc);

        Empresa empresaUpdated = this.empresaRepository.save(empresaSaved);

        assertThat(empresaUpdated).isNotNull();

        assertThat(empresaUpdated.getDescricao()).isEqualTo(desc);
    }

    @Test
    @DisplayName("Delete remove uma Empresa quando realizado com sucesso")
    void delete_RemoveEmpresa_WhenSuccessful(){

        Empresa empresaToBeSaved = EmpresaCreator.createValidEmpresa();

        Empresa empresaSaved = this.empresaRepository.save(empresaToBeSaved);

        this.empresaRepository.delete(empresaSaved);

        Optional<Empresa> empresaOptional = this.empresaRepository
                .findById(empresaSaved.getId_empresa());

        assertThat(empresaOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir uma Empresa em uso lança DataIntegrityViolationException")
    void deleteById_EmpresaThrowsDataIntegrityViolationException_WhenEmpresaIsInUse(){
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.empresaRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir uma Empresa por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_EmpresaThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.empresaRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhuma Empresa é encontrada")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<Empresa> empresaList = this.empresaRepository.findAll();

        Assertions.assertThat(empresaList).isEmpty();
    }



}