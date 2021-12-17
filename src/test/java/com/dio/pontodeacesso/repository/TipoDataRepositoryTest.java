package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.util.TipoDataCreator;
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



@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de TipoData para o banco MySql")
class TipoDataRepositoryTest extends MySqlTest{

    @Autowired
    private TipoDataRepository tipoDataRepository;

    @Test
    @DisplayName("Testa a inserção de TipoData")
    void save_PersistTipoData_WhenSuccessful(){
        TipoData tipoData = TipoDataCreator.createTipoData();
        TipoData tipoDatasalvo = this.tipoDataRepository.save(tipoData);
        Assertions.assertThat(tipoDatasalvo).isNotNull();
        Assertions.assertThat(tipoDatasalvo.getId_tipoData()).isNotNull();
        Assertions.assertThat(tipoDatasalvo.getDescricao()).isEqualTo(tipoData.getDescricao());
    }

    @Test
    @DisplayName("Testa a atualização de TipoData")
    void save_UpdateTipoData_WhenSuccessful(){

        TipoData tipoData = TipoDataCreator.createTipoData();

        TipoData tipoDatasalvo = this.tipoDataRepository.save(tipoData);

        tipoDatasalvo.setDescricao("Nova desc");

        TipoData tipoDataUpdated = this.tipoDataRepository.save(tipoDatasalvo);

        Assertions.assertThat(tipoDatasalvo).isNotNull();

        Assertions.assertThat(tipoDatasalvo.getId_tipoData()).isNotNull();

        Assertions.assertThat(tipoDataUpdated.getDescricao()).isEqualTo(tipoDatasalvo.getDescricao());
    }

    @Test
    @DisplayName("Delete remove um TipoData quando realizado com sucesso")
    void delete_RemoveTipoData_WhenSuccessful(){
        TipoData tipoData = TipoDataCreator.createTipoData();

        TipoData tipoDatasalvo = this.tipoDataRepository.save(tipoData);

        this.tipoDataRepository.delete(tipoDatasalvo);

        Optional<TipoData> tipoDataOptional = this.tipoDataRepository.findById(tipoDatasalvo.getId_tipoData());

        Assertions.assertThat(tipoDataOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um tipoData em uso lança DataIntegrityViolationException")
    void deleteById_TipoDataThrowsDataIntegrityViolationException_WhenTipoDataIsInUse(){
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.tipoDataRepository.deleteById(1L));
    }

    @Test
    @DisplayName("Tentar excluir um tipoData por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_TipoDataThrowsEmptyResultDataAccessException_WhenUnsuccessful(){
        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> this.tipoDataRepository.deleteById(1111111L));
    }

    @Test
    @DisplayName("Find By Descricao retorna uma lista de descrição quando realizado com sucesso")
    void findByDescricao_ReturnsListOfTipoData_WhenSuccessful(){
        TipoData tipoData = TipoDataCreator.createTipoData();
        TipoData tipoDatasalvo = this.tipoDataRepository.save(tipoData);

        String desc = tipoDatasalvo.getDescricao();

        List<TipoData> tipoDataList = this.tipoDataRepository.findByDescricao(desc);

        Assertions.assertThat(tipoDataList)
                .isNotEmpty()
                .contains(tipoDatasalvo);
    }

    @Test
    @DisplayName("Find By Descricao retorna uma lista vazia quando nenhum tipoData é encontrado")
    void findByDescricao_ReturnsEmptyList_WhenSuccessful(){

        List<TipoData> tipoDataList = this.tipoDataRepository.findByDescricao("4l34t0r10");

        Assertions.assertThat(tipoDataList).isEmpty();
    }

}