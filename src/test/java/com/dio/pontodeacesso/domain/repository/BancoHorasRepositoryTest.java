package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.exception.MovimentacaoNaoEncontradaException;
import com.dio.pontodeacesso.domain.exception.UsuarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayName("Testa o repositório de BancoHoras para o banco MySql")
class BancoHorasRepositoryTest extends MySqlTest {

    @Autowired
    private BancoHorasRepository bancoHorasRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Testa a inserção de um BancoHoras valido")
    void save_PersistBancoHoras_WhenSuccessful() {

        BancoHoras bancoHorasToBeSaved = createBancoHoras(10L, 1L, 1L);

        BancoHoras bancoHorasSaved = this.bancoHorasRepository.save(bancoHorasToBeSaved);

        Assertions.assertThat(bancoHorasSaved).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_bancoHoras()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_movimentacao()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_usuario()).isNotNull();
    }

    @Test
    @DisplayName("Testa a atualização de BancoHoras")
    void save_UpdateBancoHoras_WhenSuccessful() {

        BancoHoras bancoHorasToBeSaved = createBancoHoras(10L, 1L, 1L);

        BancoHoras bancoHorasSaved = this.bancoHorasRepository.save(bancoHorasToBeSaved);

        BigDecimal qtdHoras = new BigDecimal(50);

        bancoHorasSaved.setQuantidadeHoras(qtdHoras);

        BancoHoras bancoHorasUpdated = this.bancoHorasRepository.save(bancoHorasSaved);

        Assertions.assertThat(bancoHorasSaved).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_bancoHoras()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_movimentacao()).isNotNull();

        Assertions.assertThat(bancoHorasSaved.getIdBancoHoras().getId_usuario()).isNotNull();

        Assertions.assertThat(bancoHorasUpdated.getQuantidadeHoras()).isEqualTo(qtdHoras);
    }

    @Test
    @DisplayName("Testa a inserção de um BancoHoras com a FK de Usuario invalida")
    void save_PersistBancoHorasThrowUsuarioNaoEncontradoException_WhenSuccessful() {

        Assertions.assertThatExceptionOfType(UsuarioNaoEncontradoException.class)
                .isThrownBy(() -> createBancoHoras(10L, 1L, 5L));
    }

    @Test
    @DisplayName("Testa a inserção de um BancoHoras com a PK de Movimentacao invalida")
    void save_PersistBancoHorasThrowMovimentacaoNaoEncontradaException_WhenSuccessful() {

        Assertions.assertThatExceptionOfType(MovimentacaoNaoEncontradaException.class)
                .isThrownBy(() -> createBancoHoras(10L, 10L, 1L));
    }

    @Test
    @DisplayName("Delete remove um BancoHoras quando realizado com sucesso")
    void delete_RemoveBancoHoras_WhenSuccessful() {

        BancoHoras bancoHorasToBeSaved = createBancoHoras(10L, 1L, 1L);

        BancoHoras bancoHorasSaved = this.bancoHorasRepository.save(bancoHorasToBeSaved);

        this.bancoHorasRepository.delete(bancoHorasSaved);

        Optional<BancoHoras> bancoHorasOptional = this.bancoHorasRepository.findById(bancoHorasSaved.getIdBancoHoras());

        Assertions.assertThat(bancoHorasOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Tentar excluir um BancoHoras por um id inexistente lança EmptyResultDataAccessException")
    void deleteById_BancoHorasThrowsEmptyResultDataAccessException_WhenUnsuccessful() {

        Assertions.assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> this.bancoHorasRepository.deleteById(createIdBancoHoras(10L,1L,1L)));
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando nenhum BancoHoras é encontrado")
    void findAll_ReturnsEmptyList_WhenSuccessful() {

        databaseCleaner.clearTables();

        List<BancoHoras> bancoHorasList = this.bancoHorasRepository.findAll();

        Assertions.assertThat(bancoHorasList).isEmpty();
    }

    private BancoHoras.BancoHorasId createIdBancoHoras(Long idBancoHoras, Long idMov, Long idUsu){
        return BancoHoras.BancoHorasId.builder()
                .id_bancoHoras(idBancoHoras)
                .id_movimentacao(idMov)
                .id_usuario(idUsu)
                .build();
    }

    private BancoHoras createBancoHoras(Long idBancoHoras, Long idMov, Long idUsu) {

        //Para montar um id de movimentacao, primeiro preciso de um objeto válido do tipo Usuario
        Usuario usuario = this.usuarioRepository.findById(idUsu)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(idUsu));

        //As Pks válidas de movimentacao são (1,1),(2,1) e (3,2)
        Movimentacao movimentacao = Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(idMov).id_usuario(idUsu).build())
                .usuario(usuario)
                .build();
        //Para montar um id de BancoHoras, primeiro preciso de um objeto válido do tipo Movimentacao
        Movimentacao movimentacaoAtual = this.movimentacaoRepository.findById(movimentacao.getMovimentacaoId())
                .orElseThrow(() -> new MovimentacaoNaoEncontradaException(movimentacao.getMovimentacaoId()));

        return BancoHoras.builder()
                .idBancoHoras(BancoHoras.BancoHorasId.builder()
                        .id_bancoHoras(idBancoHoras)
                        .id_usuario(idUsu)
                        .id_movimentacao(idMov).build())
                .movimentacao(movimentacaoAtual)
                .dataTrabalhada(OffsetDateTime.now())
                .quantidadeHoras(new BigDecimal(6))
                .saldoHoras(new BigDecimal(2))
                .build();
    }

}