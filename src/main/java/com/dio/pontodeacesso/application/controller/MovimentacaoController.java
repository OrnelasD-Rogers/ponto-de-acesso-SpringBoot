package com.dio.pontodeacesso.application.controller;

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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoModelAssembler assembler;
    private final MovimentacaoModelDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MovimentacaoModel create(@Valid @RequestBody MovimentacaoInputModel movimentacaoInputModel) {
        Movimentacao movimentacao = desassembler.toDomainModel(movimentacaoInputModel);
        return assembler.toModel(movimentacaoService.save(movimentacao));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idMovimento}-{idUsuario}")
    public MovimentacaoModel update(@PathVariable @NotNull Long idMovimento,
                                    @PathVariable @NotNull Long idUsuario,
                                    @Valid @RequestBody MovimentacaoInputModel movimentacaoInputModel) {

        Movimentacao movimentacaoAtual = movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario));
        desassembler.copyToDomainModel(movimentacaoInputModel, movimentacaoAtual);
        return assembler.toModel(movimentacaoService.save(movimentacaoAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idMovimento}-{idUsuario}")
    public void delete(@PathVariable @NotNull Long idMovimento,
                       @PathVariable @NotNull Long idUsuario) {
        movimentacaoService.delete(getMovimentacaoId(idMovimento, idUsuario));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MovimentacaoModel> findAll() {
        return assembler.toCollectionModel(movimentacaoService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idMovimento}-{idUsuario}")
    public MovimentacaoModel getById(@PathVariable @NotNull Long idMovimento,
                                     @PathVariable @NotNull Long idUsuario) {
        return assembler.toModel(movimentacaoService.findById(getMovimentacaoId(idMovimento, idUsuario)));
    }

    private Movimentacao.MovimentacaoId getMovimentacaoId(Long id_Movimento, Long id_Usuario) {
        Movimentacao.MovimentacaoId movimentacaoId = Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(id_Movimento)
                .id_usuario(id_Usuario)
                .build();
        return movimentacaoId;
    }
}
