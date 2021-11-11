package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.model.NivelAcesso;
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


@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de NivelAcesso para o banco MySql")
class NivelAcessoRepositoryTest extends MySqlTest {

    @Autowired
    private NivelAcessoRepository nivelAcessoRepository;

    @Test
    @DisplayName("Testa a inserção de um NivelAcesso valido")
    void save_PersistNivelAcesso_WhenSuccessful(){
        NivelAcesso nivelAcessoToBeSaved =createValidNivelAcesso();

        NivelAcesso nivelAcessoSaved = this.nivelAcessoRepository.save(nivelAcessoToBeSaved);

        assertThat(nivelAcessoSaved).isNotNull();

        assertThat(nivelAcessoSaved.getId_nivelAcesso()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de NivelAcesso")
    void save_UpdateNivelAcesso_WhenSuccessful(){

        NivelAcesso nivelAcessoToBeSaved = createValidNivelAcesso();

        NivelAcesso nivelAcessoSaved = this.nivelAcessoRepository.save(nivelAcessoToBeSaved);

        String novaDesc = "Desc de atualizacao";

        nivelAcessoSaved.setDescricao(novaDesc);

        NivelAcesso nivelAcessoUpdated = this.nivelAcessoRepository.save(nivelAcessoSaved);

        Assertions.assertThat(nivelAcessoSaved).isNotNull();

        Assertions.assertThat(nivelAcessoSaved.getId_nivelAcesso()).isNotNull();

        Assertions.assertThat(nivelAcessoUpdated.getDescricao()).isEqualTo(novaDesc);
    }

    @Test
    @DisplayName("Delete remove um NivelAcesso quando realizado com sucesso")
    void delete_RemoveNivelAcesso_WhenSuccessful(){

        NivelAcesso nivelAcessoToBeSaved = createValidNivelAcesso();

        NivelAcesso nivelAcessoSaved = this.nivelAcessoRepository.save(nivelAcessoToBeSaved);

        this.nivelAcessoRepository.delete(nivelAcessoSaved);

        Optional<NivelAcesso> nivelAcessoOptional = this.nivelAcessoRepository.findById(nivelAcessoSaved.getId_nivelAcesso());

        Assertions.assertThat(nivelAcessoOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um NivelAcesso em uso lança DataIntegrityViolationException")
    void deleteById_NivelAcessoThrowsDataIntegrityViolationException_WhenNivelAcessoIsInUse(){

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.nivelAcessoRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir um NivelAcesso por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_NivelAcessoThrowsEmptyResultDataAccessException_WhenUnsuccessful(){

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(()-> this.nivelAcessoRepository.deleteById(111111111L));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhum NivelAcesso é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful(){

        databaseCleaner.clearTables();

        List<NivelAcesso> nivelAcessoList = this.nivelAcessoRepository.findAll();

        Assertions.assertThat(nivelAcessoList).isEmpty();
    }

    private NivelAcesso createValidNivelAcesso(){
        return NivelAcesso.builder()
                .descricao("Nova desc").build();
    }

}