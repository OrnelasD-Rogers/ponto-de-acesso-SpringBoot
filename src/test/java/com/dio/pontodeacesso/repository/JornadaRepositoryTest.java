package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
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
@DisplayName("Testa o repositório de JornadaTrabalho para o banco MySql")
class JornadaRepositoryTest extends MySqlTest {

    @Autowired
    private JornadaRepository jornadaRepository;

    @Test
    @DisplayName("Testa a inserção de uma JornadaTrabalho valida")
    void save_PersistJornadaTrabalho_WhenSuccessful(){
        JornadaTrabalho jornadaTrabalhoToBeSaved = createValidJornadaTrabalho();

        JornadaTrabalho jornadaTrabalhoSaved = this.jornadaRepository.save(jornadaTrabalhoToBeSaved);

        assertThat(jornadaTrabalhoSaved).isNotNull();

        assertThat(jornadaTrabalhoSaved.getId_jornada_trabalho()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de JornadaTrabalho")
    void save_UpdateJornadaTrabalho_WhenSuccessful(){
        JornadaTrabalho jornadaTrabalhoToBeSaved = createValidJornadaTrabalho();

        JornadaTrabalho jornadaTrabalhoSaved = this.jornadaRepository.save(jornadaTrabalhoToBeSaved);

        String desc = "Descricao de atualizacao";

        jornadaTrabalhoSaved.setDescricao(desc);

        JornadaTrabalho jornadaTrabalhoUpdated = this.jornadaRepository.save(jornadaTrabalhoSaved);

        assertThat(jornadaTrabalhoUpdated).isNotNull();

        assertThat(jornadaTrabalhoUpdated.getDescricao()).isEqualTo(desc);
    }

    @Test
    @DisplayName("Delete remove uma JornadaTrabalho quando realizado com sucesso")
    void delete_RemoveJornadaTrabalho_WhenSuccessful(){

        JornadaTrabalho jornadaTrabalhoToBeSaved = createValidJornadaTrabalho();

        JornadaTrabalho jornadaTrabalhoSaved = this.jornadaRepository.save(jornadaTrabalhoToBeSaved);

        this.jornadaRepository.delete(jornadaTrabalhoSaved);

        Optional<JornadaTrabalho> jornadaTrabalhoOptional = this.jornadaRepository
                .findById(jornadaTrabalhoSaved.getId_jornada_trabalho());

        assertThat(jornadaTrabalhoOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir uma JornadaTrabalho em uso lança DataIntegrityViolationException")
    void deleteById_JornadaTrabalhoThrowsDataIntegrityViolationException_WhenJornadaTrabalhoIsInUse(){
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.jornadaRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir uma JornadaTrabalho por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_JornadaTrabalhoThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.jornadaRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhuma JornadaTrabalho é encontrada")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<JornadaTrabalho> jornadaTrabalhoList = this.jornadaRepository.findAll();

        Assertions.assertThat(jornadaTrabalhoList).isEmpty();
    }

    private JornadaTrabalho createValidJornadaTrabalho(){
        return JornadaTrabalho.builder()
                .descricao("Nova Desc")
                .build();
    }

}