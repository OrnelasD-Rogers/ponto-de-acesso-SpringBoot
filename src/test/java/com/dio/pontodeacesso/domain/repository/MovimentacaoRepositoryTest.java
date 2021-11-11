package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.exception.UsuarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.model.Usuario;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Log4j2
@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de Movimentacao para o banco MySql")
class MovimentacaoRepositoryTest extends MySqlTest {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Testa a inserção de uma Movimentacao valida")
    void save_PersistMovimentacao_WhenSuccessful() {
        Movimentacao movimentacaoToBeSaved = createValidMovimentacao();

        Movimentacao movimentacaoSaved = this.movimentacaoRepository.save(movimentacaoToBeSaved);

        assertThat(movimentacaoSaved).isNotNull();

        assertThat(movimentacaoSaved.getMovimentacaoId()).isNotNull();
    }

    @Test
    @DisplayName("Testa a inserção de um Movimentacao com um Calendario invalido")
    void save_ThrowException_WhenFkCalendarioIsInvalid() {
        Movimentacao movimentacaoToBeSaved = createInvalidMovimentacao_FK_Calendario();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.movimentacaoRepository.save(movimentacaoToBeSaved));
    }

    @Test
    @DisplayName("Testa a inserção de uma Movimentacao com uma Ocorrencia invalida")
    void save_ThrowException_WhenFkOcorrenciaIsInvalid() {
        Movimentacao movimentacaoToBeSaved = createInvalidMovimentacao_FK_Ocorrencia();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.movimentacaoRepository.save(movimentacaoToBeSaved));
    }

    @Test
    @DisplayName("Testa a inserção de um Movimentacao com um Usuario invalido")
    void save_ThrowException_WhenPkUsuarioIsInvalid() {
        //Ao não encontrar um objeto Usuario válido, é lançada uma exceção
        assertThatExceptionOfType(UsuarioNaoEncontradoException.class)
                .isThrownBy(this::createInvalidMovimentacao_PK_Usuario);
    }

    @Test
    @DisplayName("Testa a atualização de Movimentacao")
    void save_UpdateMovimentacao_WhenSuccessful() {

        Movimentacao movimentacaoToBeSaved = createValidMovimentacao();

        Movimentacao movimentacaoSaved = this.movimentacaoRepository.save(movimentacaoToBeSaved);

        BigDecimal novoPeriodo = new BigDecimal(50);

        movimentacaoSaved.setPeriodo(novoPeriodo);

        Movimentacao movimentacaoUpdated = this.movimentacaoRepository.save(movimentacaoSaved);

        Assertions.assertThat(movimentacaoSaved).isNotNull();

        Assertions.assertThat(movimentacaoSaved.getPeriodo()).isNotNull();

        Assertions.assertThat(movimentacaoUpdated.getPeriodo()).isEqualTo(novoPeriodo);
    }

    @Test
    @DisplayName("Delete remove um Movimentacao quando realizado com sucesso")
    void delete_RemoveMovimentacao_WhenSuccessful() {

        Movimentacao movimentacaoToBeSaved = createValidMovimentacao();

        Movimentacao movimentacaoSaved = this.movimentacaoRepository.save(movimentacaoToBeSaved);

        this.movimentacaoRepository.delete(movimentacaoSaved);

        Optional<Movimentacao> movimentacaoOptional = this.movimentacaoRepository.findById(movimentacaoSaved.getMovimentacaoId());

        Assertions.assertThat(movimentacaoOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um Movimentacao em uso lança DataIntegrityViolationException")
    void deleteById_MovimentacaoThrowsDataIntegrityViolationException_WhenMovimentacaoIsInUse() {

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.movimentacaoRepository.deleteById(movimentacaoId(1L, 1L)));
    }

    @Test
    @DisplayName("Tentar excluir um Movimentacao por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_MovimentacaoThrowsEmptyResultDataAccessException_WhenUnsuccessful() {

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> this.movimentacaoRepository.deleteById(movimentacaoId(100L, 100L)));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhum Movimentacao é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful() {

        databaseCleaner.clearTables();

        List<Movimentacao> movimentacaoList = this.movimentacaoRepository.findAll();

        Assertions.assertThat(movimentacaoList).isEmpty();
    }

    private Movimentacao.MovimentacaoId movimentacaoId(Long idMov, Long idUsu) {
        return Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(idMov)
                .id_usuario(idUsu).build();
    }

    private Movimentacao createValidMovimentacao() {
    /*
        Pego um usuário válido primeiro pois o JPA só salva uma Entidade com chave primaria composta por uma pk
        se ele tiver um objeto válido do tipo Usuario
    */
        Long id = 1L;
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException(id));
        return Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(10L).id_usuario(id).build())
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(Ocorrencia.builder().id_ocorrencia(1L).build())
                .calendario(Calendario.builder().id_calendario(1L).build())
                .usuario(usuario)
                .build();
    }

    private Movimentacao createInvalidMovimentacao_PK_Usuario() {
    /*
        Busco por um usuário inválido primeiro pois o JPA só salva uma Entidade com chave primaria composta por uma pk
        se ele tiver um objeto válido do tipo Usuario
    */
        Long id = 1000L;
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException(id));
        return Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(1L).id_usuario(id).build())
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(Ocorrencia.builder().id_ocorrencia(1L).build())
                .calendario(Calendario.builder().id_calendario(1L).build())
                .build();
    }

    private Movimentacao createInvalidMovimentacao_FK_Ocorrencia() {
    /*
        Pego um usuário válido primeiro pois o JPA só salva uma Entidade com chave primaria composta por uma pk
        se ele tiver um objeto válido do tipo Usuario
    */
        Long id = 1L;
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException(id));
        return Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(1L).id_usuario(id).build())
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(Ocorrencia.builder().id_ocorrencia(10000L).build())
                .calendario(Calendario.builder().id_calendario(1L).build())
                .usuario(usuario)
                .build();
    }

    private Movimentacao createInvalidMovimentacao_FK_Calendario() {
                /*
        Pego um usuário válido primeiro pois o JPA só salva uma Entidade com chave primaria composta por uma pk
        se ele tiver um objeto válido do tipo Usuario
        */
        Long id = 1L;
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException(id));
        return Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(1L).id_usuario(id).build())
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(Ocorrencia.builder().id_ocorrencia(1L).build())
                .calendario(Calendario.builder().id_calendario(10000L).build())
                .usuario(usuario)
                .build();
    }

}