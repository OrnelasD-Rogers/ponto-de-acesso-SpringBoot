package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.Movimentacao;
import com.dio.pontodeacesso.model.MovimentacaoId;
import com.dio.pontodeacesso.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {
    MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Movimentacao save(Movimentacao movimentacao) {
        return  movimentacaoRepository.save(movimentacao);
    }

    public Optional<Movimentacao> findById(MovimentacaoId movimentacaoId) {
        return  movimentacaoRepository.findById(movimentacaoId);
    }

    public void delete(MovimentacaoId movimentacaoId) {
        movimentacaoRepository.deleteById(movimentacaoId);
    }

    public List<Movimentacao> findAll() {
        return movimentacaoRepository.findAll();
    }
}
