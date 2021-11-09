package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.MovimentacaoApi;
import com.dio.pontodeacesso.application.assembler.MovimentacaoModelAssembler;
import com.dio.pontodeacesso.application.assembler.MovimentacaoModelDesassembler;
import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController implements MovimentacaoApi {

    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoModelAssembler assembler;
    private final MovimentacaoModelDesassembler desassembler;

    @Override
    public List<MovimentacaoModel> findAll() {
        return assembler.toCollectionModel(movimentacaoService.findAll());
    }

    @Override
    public MovimentacaoModel findById(Long idMovimento, Long idUsuario) {
        return assembler.toModel(movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario)));
    }

    @Override
    public MovimentacaoModel create(MovimentacaoInputModel movimentacaoInputModel) {
        Movimentacao movimentacao = desassembler.toDomainModel(movimentacaoInputModel);
        return assembler.toModel(movimentacaoService.save(movimentacao));
    }

    @Override
    public MovimentacaoModel update(Long idMovimento, Long idUsuario, MovimentacaoInputModel movimentacaoInputModel) {
        Movimentacao movimentacaoAtual = movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario));
        desassembler.copyToDomainModel(movimentacaoInputModel, movimentacaoAtual);
        return assembler.toModel(movimentacaoService.save(movimentacaoAtual));
    }

    @Override
    public void delete(Long idMovimento, Long idUsuario) {
        movimentacaoService.delete(getMovimentacaoId(idMovimento, idUsuario));
    }

    private Movimentacao.MovimentacaoId getMovimentacaoId(Long id_Movimento, Long id_Usuario) {
        Movimentacao.MovimentacaoId movimentacaoId = Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(id_Movimento)
                .id_usuario(id_Usuario)
                .build();
        return movimentacaoId;
    }


}
