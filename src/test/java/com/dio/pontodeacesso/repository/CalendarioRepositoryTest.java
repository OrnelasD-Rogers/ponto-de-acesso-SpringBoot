package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.util.CalendarioCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;



@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de Calendario para o banco MySql")
class CalendarioRepositoryTest extends MySqlTest {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Test
    @DisplayName("Testa a inserção de um Calendario valido")
    void save_PersistCalendario_WhenSuccessful(){

        Calendario calendarioToBeSaved = CalendarioCreator.createValidCalendario();

        Calendario calendarioSaved = this.calendarioRepository.save(calendarioToBeSaved);

        Assertions.assertThat(calendarioSaved).isNotNull();

        Assertions.assertThat(calendarioSaved.getId_calendario()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de Calendario")
    void save_UpdateCalendario_WhenSuccessful(){

        Calendario calendarioToBeSaved = CalendarioCreator.createValidCalendario();

        Calendario calendarioSaved = this.calendarioRepository.save(calendarioToBeSaved);

        String novaDesc = "Desc de atualizacao";

        calendarioSaved.setDescricao(novaDesc);

        Calendario calendarioUpdated = this.calendarioRepository.save(calendarioSaved);

        Assertions.assertThat(calendarioSaved).isNotNull();

        Assertions.assertThat(calendarioSaved.getId_calendario()).isNotNull();

        Assertions.assertThat(calendarioUpdated.getDescricao()).isEqualTo(novaDesc);
    }

    @Test
    @DisplayName("Testa a inserção de um Calendario com a FK de TipoData invalida")
    void save_PersistCalendarioThrowDataIntegrityViolationException_WhenSuccessful(){

        Calendario calendarioToBeSaved = CalendarioCreator.createInvalidCalendario();
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(()-> this.calendarioRepository.save(calendarioToBeSaved));
    }

    @Test
    @DisplayName("Delete remove um Calendario quando realizado com sucesso")
    void delete_RemoveCalendario_WhenSuccessful(){

        Calendario calendarioToBeSaved = CalendarioCreator.createValidCalendario();

        Calendario calendarioSaved = this.calendarioRepository.save(calendarioToBeSaved);

        this.calendarioRepository.delete(calendarioSaved);

        Optional<Calendario> calendarioOptional = this.calendarioRepository.findById(calendarioSaved.getId_calendario());

        Assertions.assertThat(calendarioOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um Calendario em uso lança DataIntegrityViolationException")
    void deleteById_CalendarioThrowsDataIntegrityViolationException_WhenCalendarioIsInUse(){

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.calendarioRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir um Calendario por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_CalendarioThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.calendarioRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhum Calendario é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<Calendario> calendarioList = this.calendarioRepository.findAll();

        Assertions.assertThat(calendarioList).isEmpty();
    }


}