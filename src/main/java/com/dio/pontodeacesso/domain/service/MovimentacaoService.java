package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.MovimentacaoNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final OcorrenciaService ocorrenciaService;
    private final CalendarioService calendarioService;
    private final UsuarioService usuarioService;

    @Transactional
    public Movimentacao save(Movimentacao movimentacao) {
        ocorrenciaService.findById(movimentacao.getOcorrencia().getId_ocorrencia());
        calendarioService.findById(movimentacao.getCalendario().getId_calendario());
        movimentacao.setUsuario(usuarioService.findById(movimentacao.getMovimentacaoId().getId_usuario()));
        return  movimentacaoRepository.save(movimentacao);
    }

    public Movimentacao findById(Movimentacao.MovimentacaoId movimentacaoId) {
        return  movimentacaoRepository.findById(movimentacaoId).orElseThrow(
                () -> new MovimentacaoNaoEncontradaException(movimentacaoId)
        );
    }

    @Transactional
    public void delete(Movimentacao.MovimentacaoId movimentacaoId) {
        try {
            movimentacaoRepository.deleteById(movimentacaoId);
            movimentacaoRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
                throw new MovimentacaoNaoEncontradaException(movimentacaoId);
            } catch (DataIntegrityViolationException e){
                throw new EntidadeEmUsoException(
                        String.format("A movimenta????o com o c??digo (%d, %d) n??o pode ser removida pois est?? em uso",
                                movimentacaoId.getId_movimentacao(), movimentacaoId.getId_usuario()));
            }
    }

    public List<Movimentacao> findAll() {
        return movimentacaoRepository.findAll();
    }
}
