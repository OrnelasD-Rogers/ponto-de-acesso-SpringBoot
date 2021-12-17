package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.MovimentacaoApi;
import com.dio.pontodeacesso.application.assembler.MovimentacaoModelAssembler;
import com.dio.pontodeacesso.application.assembler.MovimentacaoModelDesassembler;
import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController implements MovimentacaoApi {

    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoModelAssembler assembler;
    private final MovimentacaoModelDesassembler desassembler;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<MovimentacaoModel> findAll() {
        return assembler.toCollectionModel(movimentacaoService.findAll());
    }

    @Override
    @GetMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public MovimentacaoModel findById(Long idMovimento, Long idUsuario) {
        return assembler.toModel(movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario)));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoModel create(MovimentacaoInputModel movimentacaoInputModel) {
        Movimentacao movimentacao = desassembler.toDomainModel(movimentacaoInputModel);
        return assembler.toModel(movimentacaoService.save(movimentacao));
    }

    @Override
    @PutMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public MovimentacaoModel update(Long idMovimento, Long idUsuario, MovimentacaoInputModel movimentacaoInputModel) {
        Movimentacao movimentacaoAtual = movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario));
        desassembler.copyToDomainModel(movimentacaoInputModel, movimentacaoAtual);
        return assembler.toModel(movimentacaoService.save(movimentacaoAtual));
    }

    @Override
    @DeleteMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idMovimento, Long idUsuario) {
        movimentacaoService.delete(getMovimentacaoId(idMovimento, idUsuario));
    }

    private Movimentacao.MovimentacaoId getMovimentacaoId(Long id_Movimento, Long id_Usuario) {
        return Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(id_Movimento)
                .id_usuario(id_Usuario)
                .build();
    }


}
