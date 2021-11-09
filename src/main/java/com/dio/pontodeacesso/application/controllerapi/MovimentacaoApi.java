package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "Movimentacao", description = "Movimentacao do ponto de acesso")
@RequestMapping("/movimentacao")
public interface MovimentacaoApi {

    ////////////// findAll

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Lista todas as movimentações",tags = {"movimentacao"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimentacaoModel.class))))})
    List<MovimentacaoModel> findAll();

    ////////////// findById

    @GetMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura uma movimentacao pelo seu id",tags = {"movimentacao"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = MovimentacaoModel.class))),
            @ApiResponse(responseCode="404", description = "Movimentacao não encontrada")})
    MovimentacaoModel findById(
            @Parameter(description = "Id de movimentacao que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idMovimento,
            @Parameter(description = "Id de usuario que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario
    );

    ////////////// insert

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona uma nova movimentacao",tags = {"movimentacao"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = MovimentacaoModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    MovimentacaoModel create(
            @Parameter(description = "movimentacao a ser adicionada",
                    required = true, schema = @Schema(implementation = MovimentacaoInputModel.class))
            @Valid @RequestBody MovimentacaoInputModel movimentacaoInputModel
    );

    ////////////// update

    @PutMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza uma movimentacao",tags = {"movimentacao"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = MovimentacaoModel.class))),
            @ApiResponse(responseCode="404", description = "Movimentacao não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    MovimentacaoModel update(
            @PathVariable @NotNull Long idMovimento,
            @PathVariable @NotNull Long idUsuario,
            @Parameter(description = "movimentacao a ser atualizada",
                    required = true, schema = @Schema(implementation = MovimentacaoInputModel.class))
            @Valid @RequestBody MovimentacaoInputModel movimentacaoInputModel
    );

    ////////////// delete

    @DeleteMapping("/{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma movimentacao",tags = {"movimentacao"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Movimentacao não encontrada")})
    void delete(
            @Parameter(description = "Id de movimentacao que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idMovimento,
            @Parameter(description = "Id de usuario que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario
    );
}
