package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.BancoHorasNaoEncontrado;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.repository.BancoHorasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BancoHorasService {

    private final BancoHorasRepository bancoHorasRepository;
    private final MovimentacaoService movimentacaoService;

    @Transactional
    public void deleteBancoHoras(BancoHoras.BancoHorasId id) {
        try {
            bancoHorasRepository.deleteById(id);
            bancoHorasRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new BancoHorasNaoEncontrado(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("O BancoHoras com o código (%d, %d, %d) não pode ser removido pois está em uso",
                            id.getId_bancoHoras(),
                            id.getId_movimentacao(),
                            id.getId_usuario()));
        }
    }

    @Transactional
    public BancoHoras saveBancoHoras(BancoHoras bancoHoras) {
        Movimentacao.MovimentacaoId movimentacaoId = Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(bancoHoras.getIdBancoHoras().getId_movimentacao())
                .id_usuario(bancoHoras.getIdBancoHoras().getId_usuario()).build();

        Movimentacao movimentacao = movimentacaoService.findById(movimentacaoId);
        bancoHoras.setMovimentacao(movimentacao);

        return  bancoHorasRepository.save(bancoHoras);
    }

    public BancoHoras findById(BancoHoras.BancoHorasId id) {
        return bancoHorasRepository.findById(id).orElseThrow(
                ()-> new BancoHorasNaoEncontrado(id)
        );
    }

    public List<BancoHoras> findAll() {
        return  bancoHorasRepository.findAll();
    }

}
